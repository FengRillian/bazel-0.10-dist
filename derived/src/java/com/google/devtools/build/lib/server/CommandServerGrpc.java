package com.google.devtools.build.lib.server;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: src/main/protobuf/command_server.proto")
public final class CommandServerGrpc {

  private CommandServerGrpc() {}

  public static final String SERVICE_NAME = "command_server.CommandServer";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.devtools.build.lib.server.CommandProtos.RunRequest,
      com.google.devtools.build.lib.server.CommandProtos.RunResponse> METHOD_RUN =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "command_server.CommandServer", "Run"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.devtools.build.lib.server.CommandProtos.RunRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.devtools.build.lib.server.CommandProtos.RunResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.devtools.build.lib.server.CommandProtos.CancelRequest,
      com.google.devtools.build.lib.server.CommandProtos.CancelResponse> METHOD_CANCEL =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "command_server.CommandServer", "Cancel"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.devtools.build.lib.server.CommandProtos.CancelRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.devtools.build.lib.server.CommandProtos.CancelResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.devtools.build.lib.server.CommandProtos.PingRequest,
      com.google.devtools.build.lib.server.CommandProtos.PingResponse> METHOD_PING =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "command_server.CommandServer", "Ping"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.devtools.build.lib.server.CommandProtos.PingRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.devtools.build.lib.server.CommandProtos.PingResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CommandServerStub newStub(io.grpc.Channel channel) {
    return new CommandServerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CommandServerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CommandServerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static CommandServerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CommandServerFutureStub(channel);
  }

  /**
   */
  public static abstract class CommandServerImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Run a Bazel command. See documentation of argument/return messages for
     * details.
     * </pre>
     */
    public void run(com.google.devtools.build.lib.server.CommandProtos.RunRequest request,
        io.grpc.stub.StreamObserver<com.google.devtools.build.lib.server.CommandProtos.RunResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RUN, responseObserver);
    }

    /**
     * <pre>
     * Cancel a currently running Bazel command. May return before the run command
     * actually terminates.
     * </pre>
     */
    public void cancel(com.google.devtools.build.lib.server.CommandProtos.CancelRequest request,
        io.grpc.stub.StreamObserver<com.google.devtools.build.lib.server.CommandProtos.CancelResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CANCEL, responseObserver);
    }

    /**
     * <pre>
     * Does not do anything. Used for liveness check.
     * </pre>
     */
    public void ping(com.google.devtools.build.lib.server.CommandProtos.PingRequest request,
        io.grpc.stub.StreamObserver<com.google.devtools.build.lib.server.CommandProtos.PingResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PING, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_RUN,
            asyncServerStreamingCall(
              new MethodHandlers<
                com.google.devtools.build.lib.server.CommandProtos.RunRequest,
                com.google.devtools.build.lib.server.CommandProtos.RunResponse>(
                  this, METHODID_RUN)))
          .addMethod(
            METHOD_CANCEL,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.devtools.build.lib.server.CommandProtos.CancelRequest,
                com.google.devtools.build.lib.server.CommandProtos.CancelResponse>(
                  this, METHODID_CANCEL)))
          .addMethod(
            METHOD_PING,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.devtools.build.lib.server.CommandProtos.PingRequest,
                com.google.devtools.build.lib.server.CommandProtos.PingResponse>(
                  this, METHODID_PING)))
          .build();
    }
  }

  /**
   */
  public static final class CommandServerStub extends io.grpc.stub.AbstractStub<CommandServerStub> {
    private CommandServerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CommandServerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CommandServerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CommandServerStub(channel, callOptions);
    }

    /**
     * <pre>
     * Run a Bazel command. See documentation of argument/return messages for
     * details.
     * </pre>
     */
    public void run(com.google.devtools.build.lib.server.CommandProtos.RunRequest request,
        io.grpc.stub.StreamObserver<com.google.devtools.build.lib.server.CommandProtos.RunResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_RUN, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Cancel a currently running Bazel command. May return before the run command
     * actually terminates.
     * </pre>
     */
    public void cancel(com.google.devtools.build.lib.server.CommandProtos.CancelRequest request,
        io.grpc.stub.StreamObserver<com.google.devtools.build.lib.server.CommandProtos.CancelResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CANCEL, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Does not do anything. Used for liveness check.
     * </pre>
     */
    public void ping(com.google.devtools.build.lib.server.CommandProtos.PingRequest request,
        io.grpc.stub.StreamObserver<com.google.devtools.build.lib.server.CommandProtos.PingResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PING, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CommandServerBlockingStub extends io.grpc.stub.AbstractStub<CommandServerBlockingStub> {
    private CommandServerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CommandServerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CommandServerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CommandServerBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Run a Bazel command. See documentation of argument/return messages for
     * details.
     * </pre>
     */
    public java.util.Iterator<com.google.devtools.build.lib.server.CommandProtos.RunResponse> run(
        com.google.devtools.build.lib.server.CommandProtos.RunRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_RUN, getCallOptions(), request);
    }

    /**
     * <pre>
     * Cancel a currently running Bazel command. May return before the run command
     * actually terminates.
     * </pre>
     */
    public com.google.devtools.build.lib.server.CommandProtos.CancelResponse cancel(com.google.devtools.build.lib.server.CommandProtos.CancelRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CANCEL, getCallOptions(), request);
    }

    /**
     * <pre>
     * Does not do anything. Used for liveness check.
     * </pre>
     */
    public com.google.devtools.build.lib.server.CommandProtos.PingResponse ping(com.google.devtools.build.lib.server.CommandProtos.PingRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PING, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CommandServerFutureStub extends io.grpc.stub.AbstractStub<CommandServerFutureStub> {
    private CommandServerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CommandServerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CommandServerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CommandServerFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Cancel a currently running Bazel command. May return before the run command
     * actually terminates.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.devtools.build.lib.server.CommandProtos.CancelResponse> cancel(
        com.google.devtools.build.lib.server.CommandProtos.CancelRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CANCEL, getCallOptions()), request);
    }

    /**
     * <pre>
     * Does not do anything. Used for liveness check.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.devtools.build.lib.server.CommandProtos.PingResponse> ping(
        com.google.devtools.build.lib.server.CommandProtos.PingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PING, getCallOptions()), request);
    }
  }

  private static final int METHODID_RUN = 0;
  private static final int METHODID_CANCEL = 1;
  private static final int METHODID_PING = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CommandServerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CommandServerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RUN:
          serviceImpl.run((com.google.devtools.build.lib.server.CommandProtos.RunRequest) request,
              (io.grpc.stub.StreamObserver<com.google.devtools.build.lib.server.CommandProtos.RunResponse>) responseObserver);
          break;
        case METHODID_CANCEL:
          serviceImpl.cancel((com.google.devtools.build.lib.server.CommandProtos.CancelRequest) request,
              (io.grpc.stub.StreamObserver<com.google.devtools.build.lib.server.CommandProtos.CancelResponse>) responseObserver);
          break;
        case METHODID_PING:
          serviceImpl.ping((com.google.devtools.build.lib.server.CommandProtos.PingRequest) request,
              (io.grpc.stub.StreamObserver<com.google.devtools.build.lib.server.CommandProtos.PingResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class CommandServerDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.google.devtools.build.lib.server.CommandProtos.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CommandServerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CommandServerDescriptorSupplier())
              .addMethod(METHOD_RUN)
              .addMethod(METHOD_CANCEL)
              .addMethod(METHOD_PING)
              .build();
        }
      }
    }
    return result;
  }
}
