package project.networkapi.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: network_api.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class NetworkSubmissionServiceGrpc {

  private NetworkSubmissionServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "project.networkapi.grpc.NetworkSubmissionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<project.networkapi.grpc.SubmitRequest,
      project.networkapi.grpc.SubmitResponse> getSubmitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Submit",
      requestType = project.networkapi.grpc.SubmitRequest.class,
      responseType = project.networkapi.grpc.SubmitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<project.networkapi.grpc.SubmitRequest,
      project.networkapi.grpc.SubmitResponse> getSubmitMethod() {
    io.grpc.MethodDescriptor<project.networkapi.grpc.SubmitRequest, project.networkapi.grpc.SubmitResponse> getSubmitMethod;
    if ((getSubmitMethod = NetworkSubmissionServiceGrpc.getSubmitMethod) == null) {
      synchronized (NetworkSubmissionServiceGrpc.class) {
        if ((getSubmitMethod = NetworkSubmissionServiceGrpc.getSubmitMethod) == null) {
          NetworkSubmissionServiceGrpc.getSubmitMethod = getSubmitMethod =
              io.grpc.MethodDescriptor.<project.networkapi.grpc.SubmitRequest, project.networkapi.grpc.SubmitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Submit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  project.networkapi.grpc.SubmitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  project.networkapi.grpc.SubmitResponse.getDefaultInstance()))
              .build();
        }
      }
    }
    return getSubmitMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static NetworkSubmissionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NetworkSubmissionServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NetworkSubmissionServiceStub>() {
        @java.lang.Override
        public NetworkSubmissionServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NetworkSubmissionServiceStub(channel, callOptions);
        }
      };
    return NetworkSubmissionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static NetworkSubmissionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NetworkSubmissionServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NetworkSubmissionServiceBlockingStub>() {
        @java.lang.Override
        public NetworkSubmissionServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NetworkSubmissionServiceBlockingStub(channel, callOptions);
        }
      };
    return NetworkSubmissionServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static NetworkSubmissionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<NetworkSubmissionServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<NetworkSubmissionServiceFutureStub>() {
        @java.lang.Override
        public NetworkSubmissionServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new NetworkSubmissionServiceFutureStub(channel, callOptions);
        }
      };
    return NetworkSubmissionServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void submit(project.networkapi.grpc.SubmitRequest request,
        io.grpc.stub.StreamObserver<project.networkapi.grpc.SubmitResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubmitMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service NetworkSubmissionService.
   */
  public static abstract class NetworkSubmissionServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return NetworkSubmissionServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service NetworkSubmissionService.
   */
  public static final class NetworkSubmissionServiceStub
      extends io.grpc.stub.AbstractAsyncStub<NetworkSubmissionServiceStub> {
    private NetworkSubmissionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NetworkSubmissionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NetworkSubmissionServiceStub(channel, callOptions);
    }

    /**
     */
    public void submit(project.networkapi.grpc.SubmitRequest request,
        io.grpc.stub.StreamObserver<project.networkapi.grpc.SubmitResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSubmitMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service NetworkSubmissionService.
   */
  public static final class NetworkSubmissionServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<NetworkSubmissionServiceBlockingStub> {
    private NetworkSubmissionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NetworkSubmissionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NetworkSubmissionServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public project.networkapi.grpc.SubmitResponse submit(project.networkapi.grpc.SubmitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSubmitMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service NetworkSubmissionService.
   */
  public static final class NetworkSubmissionServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<NetworkSubmissionServiceFutureStub> {
    private NetworkSubmissionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected NetworkSubmissionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new NetworkSubmissionServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<project.networkapi.grpc.SubmitResponse> submit(
        project.networkapi.grpc.SubmitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSubmitMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SUBMIT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBMIT:
          serviceImpl.submit((project.networkapi.grpc.SubmitRequest) request,
              (io.grpc.stub.StreamObserver<project.networkapi.grpc.SubmitResponse>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSubmitMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              project.networkapi.grpc.SubmitRequest,
              project.networkapi.grpc.SubmitResponse>(
                service, METHODID_SUBMIT)))
        .build();
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (NetworkSubmissionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getSubmitMethod())
              .build();
        }
      }
    }
    return result;
  }
}
