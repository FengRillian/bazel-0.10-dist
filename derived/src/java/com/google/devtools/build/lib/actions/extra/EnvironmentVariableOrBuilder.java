// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: src/main/protobuf/extra_actions_base.proto

package com.google.devtools.build.lib.actions.extra;

public interface EnvironmentVariableOrBuilder extends
    // @@protoc_insertion_point(interface_extends:blaze.EnvironmentVariable)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * It is possible that this name is not a valid variable identifier.
   * </pre>
   *
   * <code>required string name = 1;</code>
   */
  boolean hasName();
  /**
   * <pre>
   * It is possible that this name is not a valid variable identifier.
   * </pre>
   *
   * <code>required string name = 1;</code>
   */
  java.lang.String getName();
  /**
   * <pre>
   * It is possible that this name is not a valid variable identifier.
   * </pre>
   *
   * <code>required string name = 1;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   * The value is unescaped and unquoted.
   * </pre>
   *
   * <code>required string value = 2;</code>
   */
  boolean hasValue();
  /**
   * <pre>
   * The value is unescaped and unquoted.
   * </pre>
   *
   * <code>required string value = 2;</code>
   */
  java.lang.String getValue();
  /**
   * <pre>
   * The value is unescaped and unquoted.
   * </pre>
   *
   * <code>required string value = 2;</code>
   */
  com.google.protobuf.ByteString
      getValueBytes();
}