// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/watcher/v1/watch.proto

package com.google.watcher.v1;

public interface ChangeOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.watcher.v1.Change)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Name of the element, interpreted relative to the entity's actual
   * name. "" refers to the entity itself. The element name is a valid
   * UTF-8 string.
   * </pre>
   *
   * <code>string element = 1;</code>
   */
  java.lang.String getElement();
  /**
   * <pre>
   * Name of the element, interpreted relative to the entity's actual
   * name. "" refers to the entity itself. The element name is a valid
   * UTF-8 string.
   * </pre>
   *
   * <code>string element = 1;</code>
   */
  com.google.protobuf.ByteString
      getElementBytes();

  /**
   * <pre>
   * The state of the `element`.
   * </pre>
   *
   * <code>.google.watcher.v1.Change.State state = 2;</code>
   */
  int getStateValue();
  /**
   * <pre>
   * The state of the `element`.
   * </pre>
   *
   * <code>.google.watcher.v1.Change.State state = 2;</code>
   */
  com.google.watcher.v1.Change.State getState();

  /**
   * <pre>
   * The actual change data. This field is present only when `state() == EXISTS`
   * or `state() == ERROR`. Please see [google.protobuf.Any][google.protobuf.Any] about how to use
   * the Any type.
   * </pre>
   *
   * <code>.google.protobuf.Any data = 6;</code>
   */
  boolean hasData();
  /**
   * <pre>
   * The actual change data. This field is present only when `state() == EXISTS`
   * or `state() == ERROR`. Please see [google.protobuf.Any][google.protobuf.Any] about how to use
   * the Any type.
   * </pre>
   *
   * <code>.google.protobuf.Any data = 6;</code>
   */
  com.google.protobuf.Any getData();
  /**
   * <pre>
   * The actual change data. This field is present only when `state() == EXISTS`
   * or `state() == ERROR`. Please see [google.protobuf.Any][google.protobuf.Any] about how to use
   * the Any type.
   * </pre>
   *
   * <code>.google.protobuf.Any data = 6;</code>
   */
  com.google.protobuf.AnyOrBuilder getDataOrBuilder();

  /**
   * <pre>
   * If present, provides a compact representation of all the messages that have
   * been received by the caller for the given entity, e.g., it could be a
   * sequence number or a multi-part timestamp/version vector. This marker can
   * be provided in the Request message, allowing the caller to resume the stream
   * watching at a specific point without fetching the initial state.
   * </pre>
   *
   * <code>bytes resume_marker = 4;</code>
   */
  com.google.protobuf.ByteString getResumeMarker();

  /**
   * <pre>
   * If true, this Change is followed by more Changes that are in the same group
   * as this Change.
   * </pre>
   *
   * <code>bool continued = 5;</code>
   */
  boolean getContinued();
}
