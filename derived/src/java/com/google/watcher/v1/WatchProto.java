// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/watcher/v1/watch.proto

package com.google.watcher.v1;

public final class WatchProto {
  private WatchProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_watcher_v1_Request_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_watcher_v1_Request_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_watcher_v1_ChangeBatch_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_watcher_v1_ChangeBatch_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_watcher_v1_Change_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_watcher_v1_Change_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\035google/watcher/v1/watch.proto\022\021google." +
      "watcher.v1\032\034google/api/annotations.proto" +
      "\032\031google/protobuf/any.proto\032\033google/prot" +
      "obuf/empty.proto\"0\n\007Request\022\016\n\006target\030\001 " +
      "\001(\t\022\025\n\rresume_marker\030\002 \001(\014\"9\n\013ChangeBatc" +
      "h\022*\n\007changes\030\001 \003(\0132\031.google.watcher.v1.C" +
      "hange\"\346\001\n\006Change\022\017\n\007element\030\001 \001(\t\022.\n\005sta" +
      "te\030\002 \001(\0162\037.google.watcher.v1.Change.Stat" +
      "e\022\"\n\004data\030\006 \001(\0132\024.google.protobuf.Any\022\025\n" +
      "\rresume_marker\030\004 \001(\014\022\021\n\tcontinued\030\005 \001(\010\"",
      "M\n\005State\022\n\n\006EXISTS\020\000\022\022\n\016DOES_NOT_EXIST\020\001" +
      "\022\031\n\025INITIAL_STATE_SKIPPED\020\002\022\t\n\005ERROR\020\0032c" +
      "\n\007Watcher\022X\n\005Watch\022\032.google.watcher.v1.R" +
      "equest\032\036.google.watcher.v1.ChangeBatch\"\021" +
      "\202\323\344\223\002\013\022\t/v1/watch0\001B_\n\025com.google.watche" +
      "r.v1B\nWatchProtoP\001Z8google.golang.org/ge" +
      "nproto/googleapis/watcher/v1;watcherb\006pr" +
      "oto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.api.AnnotationsProto.getDescriptor(),
          com.google.protobuf.AnyProto.getDescriptor(),
          com.google.protobuf.EmptyProto.getDescriptor(),
        }, assigner);
    internal_static_google_watcher_v1_Request_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_google_watcher_v1_Request_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_watcher_v1_Request_descriptor,
        new java.lang.String[] { "Target", "ResumeMarker", });
    internal_static_google_watcher_v1_ChangeBatch_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_google_watcher_v1_ChangeBatch_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_watcher_v1_ChangeBatch_descriptor,
        new java.lang.String[] { "Changes", });
    internal_static_google_watcher_v1_Change_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_google_watcher_v1_Change_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_watcher_v1_Change_descriptor,
        new java.lang.String[] { "Element", "State", "Data", "ResumeMarker", "Continued", });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.google.api.AnnotationsProto.http);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    com.google.api.AnnotationsProto.getDescriptor();
    com.google.protobuf.AnyProto.getDescriptor();
    com.google.protobuf.EmptyProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
