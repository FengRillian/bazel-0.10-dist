// Copyright 2016 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.build.lib.rules.objc;

import static com.google.devtools.build.lib.rules.objc.AppleBinaryRule.BUNDLE_LOADER_ATTR_NAME;
import static com.google.devtools.build.lib.rules.objc.ObjcProvider.MULTI_ARCH_LINKED_BINARIES;
import static com.google.devtools.build.lib.rules.objc.ObjcRuleClasses.DylibDependingRule.DYLIBS_ATTR_NAME;
import static com.google.devtools.build.lib.syntax.Type.STRING;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.devtools.build.lib.actions.Artifact;
import com.google.devtools.build.lib.analysis.ConfiguredTarget;
import com.google.devtools.build.lib.analysis.RuleConfiguredTargetBuilder;
import com.google.devtools.build.lib.analysis.RuleConfiguredTargetFactory;
import com.google.devtools.build.lib.analysis.RuleContext;
import com.google.devtools.build.lib.analysis.TransitiveInfoCollection;
import com.google.devtools.build.lib.analysis.config.BuildConfiguration;
import com.google.devtools.build.lib.analysis.configuredtargets.RuleConfiguredTarget.Mode;
import com.google.devtools.build.lib.analysis.test.InstrumentedFilesCollector;
import com.google.devtools.build.lib.analysis.test.InstrumentedFilesProvider;
import com.google.devtools.build.lib.collect.nestedset.NestedSet;
import com.google.devtools.build.lib.collect.nestedset.NestedSetBuilder;
import com.google.devtools.build.lib.packages.NativeInfo;
import com.google.devtools.build.lib.rules.apple.AppleCommandLineOptions.AppleBitcodeMode;
import com.google.devtools.build.lib.rules.apple.AppleConfiguration;
import com.google.devtools.build.lib.rules.apple.ApplePlatform;
import com.google.devtools.build.lib.rules.apple.ApplePlatform.PlatformType;
import com.google.devtools.build.lib.rules.cpp.CcToolchainProvider;
import com.google.devtools.build.lib.rules.objc.AppleDebugOutputsProvider.OutputType;
import com.google.devtools.build.lib.rules.objc.CompilationSupport.ExtraLinkArgs;
import com.google.devtools.build.lib.rules.objc.MultiArchBinarySupport.DependencySpecificConfiguration;
import java.util.Map;
import java.util.TreeMap;

/** Implementation for the "apple_binary" rule. */
public class AppleBinary implements RuleConfiguredTargetFactory {

  /** Type of linked binary that apple_binary may create. */
  enum BinaryType {

    /**
     * Binaries that can be loaded by other binaries at runtime, and which can't be directly
     * executed by the operating system. When linking, a bundle_loader binary may be passed which
     * signals the linker on where to look for unimplemented symbols, basically declaring that the
     * bundle should be loaded by that binary. Bundle binaries are usually found in Plugins, and one
     * common use case is tests. Tests are bundled into an .xctest bundle which contains the test
     * binary along with required resources. The test bundle is then loaded and run during test
     * execution.
     */
    LOADABLE_BUNDLE,

    /**
     * Binaries that can be run directly by the operating system. They implement the main method
     * that is the entry point to the program. In Apple apps, they are usually distributed in .app
     * bundles, which are directories that contain the executable along with required resources to
     * run.
     */
    EXECUTABLE,

    /**
     * Binaries meant to be loaded at load time (when the operating system is loading the binary
     * into memory), which cannot be unloaded. They are usually distributed in frameworks, which are
     * .framework bundles that contain the dylib as well as well as required resources to run.
     */
    DYLIB;

    @Override
    public String toString() {
      return name().toLowerCase();
    }

    /**
     * Returns the {@link BinaryType} with given name (case insensitive).
     *
     * @throws IllegalArgumentException if the name does not match a valid platform type.
     */
    public static BinaryType fromString(String name) {
      for (BinaryType binaryType : BinaryType.values()) {
        if (name.equalsIgnoreCase(binaryType.toString())) {
          return binaryType;
        }
      }
      throw new IllegalArgumentException(String.format("Unsupported binary type \"%s\"", name));
    }

    /** Returns the enum values as a list of strings for validation. */
    static Iterable<String> getValues() {
      return Iterables.transform(ImmutableList.copyOf(values()), Functions.toStringFunction());
    }
  }

  @VisibleForTesting
  static final String BUNDLE_LOADER_NOT_IN_BUNDLE_ERROR =
      "Can only use bundle_loader when binary_type is bundle.";

  @Override
  public final ConfiguredTarget create(RuleContext ruleContext)
      throws InterruptedException, RuleErrorException {
    AppleBinaryOutput appleBinaryOutput = linkMultiArchBinary(ruleContext);

    return ruleConfiguredTargetFromProvider(ruleContext, appleBinaryOutput);
  }

  /**
   * Links a (potentially multi-architecture) binary targeting Apple platforms.
   *
   * <p>This method comprises a bulk of the logic of the {@code apple_binary} rule, and is
   * statically available so that it may be referenced by Skylark APIs that replicate its
   * functionality.
   *
   * @param ruleContext the current rule context
   * @return a tuple containing all necessary information about the linked binary
   */
  public static AppleBinaryOutput linkMultiArchBinary(RuleContext ruleContext)
      throws InterruptedException, RuleErrorException {
    MultiArchSplitTransitionProvider.validateMinimumOs(ruleContext);
    PlatformType platformType = MultiArchSplitTransitionProvider.getPlatformType(ruleContext);

    AppleConfiguration appleConfiguration = ruleContext.getFragment(AppleConfiguration.class);

    ApplePlatform platform = appleConfiguration.getMultiArchPlatform(platformType);
    ImmutableListMultimap<BuildConfiguration, ObjcProvider> configurationToNonPropagatedObjcMap =
        ruleContext.getPrerequisitesByConfiguration(
            "non_propagated_deps", Mode.SPLIT, ObjcProvider.SKYLARK_CONSTRUCTOR);
    ImmutableListMultimap<BuildConfiguration, TransitiveInfoCollection> configToDepsCollectionMap =
        ruleContext.getPrerequisitesByConfiguration("deps", Mode.SPLIT);

    ImmutableMap<BuildConfiguration, CcToolchainProvider> childConfigurations =
        MultiArchBinarySupport.getChildConfigurationsAndToolchains(ruleContext);
    Artifact outputArtifact =
        ObjcRuleClasses.intermediateArtifacts(ruleContext).combinedArchitectureBinary();

    MultiArchBinarySupport multiArchBinarySupport = new MultiArchBinarySupport(ruleContext);

    ImmutableSet<DependencySpecificConfiguration> dependencySpecificConfigurations =
        multiArchBinarySupport.getDependencySpecificConfigurations(
            childConfigurations,
            configToDepsCollectionMap,
            configurationToNonPropagatedObjcMap,
            getDylibProviderTargets(ruleContext));

    Map<String, NestedSet<Artifact>> outputGroupCollector = new TreeMap<>();
    NestedSet<Artifact> binariesToLipo =
        multiArchBinarySupport.registerActions(
            platform,
            getExtraLinkArgs(ruleContext),
            dependencySpecificConfigurations,
            getExtraLinkInputs(ruleContext),
            configToDepsCollectionMap,
            outputGroupCollector);

    new LipoSupport(ruleContext)
        .registerCombineArchitecturesAction(
            binariesToLipo,
            outputArtifact,
            platform);

    ObjcProvider.Builder objcProviderBuilder = new ObjcProvider.Builder();
    for (DependencySpecificConfiguration dependencySpecificConfiguration :
        dependencySpecificConfigurations) {
      objcProviderBuilder.addTransitiveAndPropagate(
          dependencySpecificConfiguration.objcProviderWithDylibSymbols());
    }
    objcProviderBuilder.add(MULTI_ARCH_LINKED_BINARIES, outputArtifact);

    ObjcProvider objcProvider = objcProviderBuilder.build();
    NativeInfo binaryInfoProvider;

    switch (getBinaryType(ruleContext)) {
      case EXECUTABLE:
        binaryInfoProvider =
            new AppleExecutableBinaryProvider(outputArtifact, objcProvider);
        break;
      case DYLIB:
        binaryInfoProvider =
            new AppleDylibBinaryProvider(outputArtifact, objcProvider);
        break;
      case LOADABLE_BUNDLE:
        binaryInfoProvider =
            new AppleLoadableBundleBinaryProvider(outputArtifact, objcProvider);
        break;
      default:
        ruleContext.ruleError("Unhandled binary type " + getBinaryType(ruleContext));
        throw new RuleErrorException();
    }

    AppleDebugOutputsProvider.Builder builder = AppleDebugOutputsProvider.Builder.create();

    for (DependencySpecificConfiguration dependencySpecificConfiguration :
        dependencySpecificConfigurations) {
      AppleConfiguration childAppleConfig =
          dependencySpecificConfiguration.config().getFragment(AppleConfiguration.class);
      ObjcConfiguration childObjcConfig =
          dependencySpecificConfiguration.config().getFragment(ObjcConfiguration.class);
      IntermediateArtifacts intermediateArtifacts =
          new IntermediateArtifacts(
              ruleContext, /*archiveFileNameSuffix*/
              "", /*outputPrefix*/
              "",
              dependencySpecificConfiguration.config());
      String arch = childAppleConfig.getSingleArchitecture();

      if (childAppleConfig.getBitcodeMode() == AppleBitcodeMode.EMBEDDED) {
        Artifact bitcodeSymbol = intermediateArtifacts.bitcodeSymbolMap();
        builder.addOutput(arch, OutputType.BITCODE_SYMBOLS, bitcodeSymbol);
      }
      if (childObjcConfig.generateDsym()) {
        Artifact dsymBinary = intermediateArtifacts.dsymSymbol(DsymOutputType.APP);
        builder.addOutput(arch, OutputType.DSYM_BINARY, dsymBinary);
      }
      if (childObjcConfig.generateLinkmap()) {
        Artifact linkmap = intermediateArtifacts.linkmap();
        builder.addOutput(arch, OutputType.LINKMAP, linkmap);
      }
    }

    return new AppleBinaryOutput(binaryInfoProvider, builder.build(), outputGroupCollector);
  }

  private static ExtraLinkArgs getExtraLinkArgs(RuleContext ruleContext) throws RuleErrorException {
    BinaryType binaryType = getBinaryType(ruleContext);

    ImmutableList.Builder<String> extraLinkArgs = new ImmutableList.Builder<>();

    boolean didProvideBundleLoader =
        ruleContext.attributes().isAttributeValueExplicitlySpecified(BUNDLE_LOADER_ATTR_NAME);

    if (didProvideBundleLoader && binaryType != BinaryType.LOADABLE_BUNDLE) {
      ruleContext.throwWithRuleError(BUNDLE_LOADER_NOT_IN_BUNDLE_ERROR);
    }

    switch (binaryType) {
      case LOADABLE_BUNDLE:
        extraLinkArgs.add("-bundle");
        extraLinkArgs.add("-Xlinker", "-rpath", "-Xlinker", "@loader_path/Frameworks");
        if (didProvideBundleLoader) {
          AppleExecutableBinaryProvider executableProvider =
              ruleContext.getPrerequisite(
                  BUNDLE_LOADER_ATTR_NAME, Mode.TARGET,
                  AppleExecutableBinaryProvider.SKYLARK_CONSTRUCTOR);
          extraLinkArgs.add(
              "-bundle_loader", executableProvider.getAppleExecutableBinary().getExecPathString());
        }
        break;
      case DYLIB:
        extraLinkArgs.add("-dynamiclib");
        break;
      case EXECUTABLE:
        break;
    }

    return new ExtraLinkArgs(extraLinkArgs.build());
  }

  private static Iterable<TransitiveInfoCollection> getDylibProviderTargets(
      RuleContext ruleContext) {
    return ImmutableList.<TransitiveInfoCollection>builder()
        .addAll(ruleContext.getPrerequisites(DYLIBS_ATTR_NAME, Mode.TARGET))
        .addAll(ruleContext.getPrerequisites(BUNDLE_LOADER_ATTR_NAME, Mode.TARGET))
        .build();
  }

  private static Iterable<Artifact> getExtraLinkInputs(RuleContext ruleContext) {
    AppleExecutableBinaryProvider executableProvider =
        ruleContext.getPrerequisite(
            BUNDLE_LOADER_ATTR_NAME, Mode.TARGET,
            AppleExecutableBinaryProvider.SKYLARK_CONSTRUCTOR);
    if (executableProvider != null) {
      return ImmutableSet.<Artifact>of(executableProvider.getAppleExecutableBinary());
    }
    return ImmutableSet.<Artifact>of();
  }

  private static BinaryType getBinaryType(RuleContext ruleContext) {
    String binaryTypeString =
        ruleContext.attributes().get(AppleBinaryRule.BINARY_TYPE_ATTR, STRING);
    return BinaryType.fromString(binaryTypeString);
  }

  private static ConfiguredTarget ruleConfiguredTargetFromProvider(
      RuleContext ruleContext, AppleBinaryOutput appleBinaryOutput) throws RuleErrorException {
    NativeInfo nativeInfo = appleBinaryOutput.getBinaryInfoProvider();
    AppleConfiguration appleConfiguration = ruleContext.getFragment(AppleConfiguration.class);

    ObjcProvider objcProvider = null;
    Artifact outputArtifact;

    switch (getBinaryType(ruleContext)) {
      case EXECUTABLE:
        AppleExecutableBinaryProvider executableProvider =
            (AppleExecutableBinaryProvider) nativeInfo;
        objcProvider = executableProvider.getDepsObjcProvider();
        outputArtifact = executableProvider.getAppleExecutableBinary();
        break;
      case DYLIB:
        AppleDylibBinaryProvider dylibProvider = (AppleDylibBinaryProvider) nativeInfo;
        objcProvider = dylibProvider.getDepsObjcProvider();
        outputArtifact = dylibProvider.getAppleDylibBinary();
        break;
      case LOADABLE_BUNDLE:
        AppleLoadableBundleBinaryProvider loadableBundleProvider =
            (AppleLoadableBundleBinaryProvider) nativeInfo;
        outputArtifact = loadableBundleProvider.getAppleLoadableBundleBinary();
        break;
      default:
        ruleContext.ruleError("Unhandled binary type " + getBinaryType(ruleContext));
        throw new RuleErrorException();
    }

    NestedSetBuilder<Artifact> filesToBuild =
        NestedSetBuilder.<Artifact>stableOrder().add(outputArtifact);

    RuleConfiguredTargetBuilder targetBuilder =
        ObjcRuleClasses.ruleConfiguredTarget(ruleContext, filesToBuild.build());

    if (appleConfiguration.shouldLinkingRulesPropagateObjc() && objcProvider != null) {
      targetBuilder.addNativeDeclaredProvider(objcProvider);
    }

    InstrumentedFilesProvider instrumentedFilesProvider =
        InstrumentedFilesCollector.forward(ruleContext, "deps", "bundle_loader");

    return targetBuilder.addProvider(InstrumentedFilesProvider.class, instrumentedFilesProvider)
        .addNativeDeclaredProvider(nativeInfo)
        .addNativeDeclaredProvider(appleBinaryOutput.getDebugOutputsProvider())
        .addOutputGroups(appleBinaryOutput.getOutputGroups())
        .build();

  }

  /**
   * The set of rule outputs propagated by the {@code apple_binary} rule.
   */
  public static class AppleBinaryOutput {
    private final NativeInfo binaryInfoProvider;
    private final AppleDebugOutputsProvider debugOutputsProvider;
    private final Map<String, NestedSet<Artifact>> outputGroups;

    private AppleBinaryOutput(NativeInfo binaryInfoProvider,
        AppleDebugOutputsProvider debugOutputsProvider,
        Map<String, NestedSet<Artifact>> outputGroups) {
      this.binaryInfoProvider = binaryInfoProvider;
      this.debugOutputsProvider = debugOutputsProvider;
      this.outputGroups = outputGroups;
    }

    /**
     * Returns a {@link NativeInfo} possessing information about the linked binary. Depending
     * on the type of binary, this may be either a {@link AppleExecutableBinaryProvider}, a
     * {@link AppleDylibBinaryProvider}, or a {@link AppleLoadableBundleBinaryProvider}.
     */
    public NativeInfo getBinaryInfoProvider() {
      return binaryInfoProvider;
    }

    /**
     * Returns a {@link AppleDebugOutputsProvider} containing debug information about the linked
     * binary.
     */
    public AppleDebugOutputsProvider getDebugOutputsProvider() {
      return debugOutputsProvider;
    }

    /**
     * Returns a map from output group name to set of artifacts belonging to this output group.
     * This should be added to configured target information using
     * {@link RuleConfiguredTargetBuilder#addOutputGroups(Map)}.
     */
    public Map<String, NestedSet<Artifact>> getOutputGroups() {
      return outputGroups;
    }
  }
}