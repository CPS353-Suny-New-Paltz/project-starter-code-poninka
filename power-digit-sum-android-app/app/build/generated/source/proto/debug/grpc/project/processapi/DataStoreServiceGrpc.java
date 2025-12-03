package project.processapi;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.64.0)",
    comments = "Source: datastore_api.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class DataStoreServiceGrpc {

  private DataStoreServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "project.processapi.DataStoreService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<project.processapi.LoadInputRequest,
      project.processapi.LoadInputResponse> getLoadInputMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LoadInput",
      requestType = project.processapi.LoadInputRequest.class,
      responseType = project.processapi.LoadInputResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<project.processapi.LoadInputRequest,
      project.processapi.LoadInputResponse> getLoadInputMethod() {
    io.grpc.MethodDescriptor<project.processapi.LoadInputRequest, project.processapi.LoadInputResponse> getLoadInputMethod;
    if ((getLoadInputMethod = DataStoreServiceGrpc.getLoadInputMethod) == null) {
      synchronized (DataStoreServiceGrpc.class) {
        if ((getLoadInputMethod = DataStoreServiceGrpc.getLoadInputMethod) == null) {
          DataStoreServiceGrpc.getLoadInputMethod = getLoadInputMethod =
              io.grpc.MethodDescriptor.<project.processapi.LoadInputRequest, project.processapi.LoadInputResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LoadInput"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  project.processapi.LoadInputRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  project.processapi.LoadInputResponse.getDefaultInstance()))
              .build();
        }
      }
    }
    return getLoadInputMethod;
  }

  private static volatile io.grpc.MethodDescriptor<project.processapi.SaveOutputRequest,
      project.processapi.SaveOutputResponse> getSaveOutputMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveOutput",
      requestType = project.processapi.SaveOutputRequest.class,
      responseType = project.processapi.SaveOutputResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<project.processapi.SaveOutputRequest,
      project.processapi.SaveOutputResponse> getSaveOutputMethod() {
    io.grpc.MethodDescriptor<project.processapi.SaveOutputRequest, project.processapi.SaveOutputResponse> getSaveOutputMethod;
    if ((getSaveOutputMethod = DataStoreServiceGrpc.getSaveOutputMethod) == null) {
      synchronized (DataStoreServiceGrpc.class) {
        if ((getSaveOutputMethod = DataStoreServiceGrpc.getSaveOutputMethod) == null) {
          DataStoreServiceGrpc.getSaveOutputMethod = getSaveOutputMethod =
              io.grpc.MethodDescriptor.<project.processapi.SaveOutputRequest, project.processapi.SaveOutputResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveOutput"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  project.processapi.SaveOutputRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  project.processapi.SaveOutputResponse.getDefaultInstance()))
              .build();
        }
      }
    }
    return getSaveOutputMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DataStoreServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataStoreServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataStoreServiceStub>() {
        @java.lang.Override
        public DataStoreServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataStoreServiceStub(channel, callOptions);
        }
      };
    return DataStoreServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DataStoreServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataStoreServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataStoreServiceBlockingStub>() {
        @java.lang.Override
        public DataStoreServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataStoreServiceBlockingStub(channel, callOptions);
        }
      };
    return DataStoreServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DataStoreServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DataStoreServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DataStoreServiceFutureStub>() {
        @java.lang.Override
        public DataStoreServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DataStoreServiceFutureStub(channel, callOptions);
        }
      };
    return DataStoreServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void loadInput(project.processapi.LoadInputRequest request,
        io.grpc.stub.StreamObserver<project.processapi.LoadInputResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoadInputMethod(), responseObserver);
    }

    /**
     */
    default void saveOutput(project.processapi.SaveOutputRequest request,
        io.grpc.stub.StreamObserver<project.processapi.SaveOutputResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveOutputMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service DataStoreService.
   */
  public static abstract class DataStoreServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return DataStoreServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service DataStoreService.
   */
  public static final class DataStoreServiceStub
      extends io.grpc.stub.AbstractAsyncStub<DataStoreServiceStub> {
    private DataStoreServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataStoreServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataStoreServiceStub(channel, callOptions);
    }

    /**
     */
    public void loadInput(project.processapi.LoadInputRequest request,
        io.grpc.stub.StreamObserver<project.processapi.LoadInputResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoadInputMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveOutput(project.processapi.SaveOutputRequest request,
        io.grpc.stub.StreamObserver<project.processapi.SaveOutputResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveOutputMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service DataStoreService.
   */
  public static final class DataStoreServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<DataStoreServiceBlockingStub> {
    private DataStoreServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataStoreServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataStoreServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public project.processapi.LoadInputResponse loadInput(project.processapi.LoadInputRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoadInputMethod(), getCallOptions(), request);
    }

    /**
     */
    public project.processapi.SaveOutputResponse saveOutput(project.processapi.SaveOutputRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveOutputMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service DataStoreService.
   */
  public static final class DataStoreServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<DataStoreServiceFutureStub> {
    private DataStoreServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DataStoreServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DataStoreServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<project.processapi.LoadInputResponse> loadInput(
        project.processapi.LoadInputRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoadInputMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<project.processapi.SaveOutputResponse> saveOutput(
        project.processapi.SaveOutputRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveOutputMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOAD_INPUT = 0;
  private static final int METHODID_SAVE_OUTPUT = 1;

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
        case METHODID_LOAD_INPUT:
          serviceImpl.loadInput((project.processapi.LoadInputRequest) request,
              (io.grpc.stub.StreamObserver<project.processapi.LoadInputResponse>) responseObserver);
          break;
        case METHODID_SAVE_OUTPUT:
          serviceImpl.saveOutput((project.processapi.SaveOutputRequest) request,
              (io.grpc.stub.StreamObserver<project.processapi.SaveOutputResponse>) responseObserver);
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
          getLoadInputMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              project.processapi.LoadInputRequest,
              project.processapi.LoadInputResponse>(
                service, METHODID_LOAD_INPUT)))
        .addMethod(
          getSaveOutputMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              project.processapi.SaveOutputRequest,
              project.processapi.SaveOutputResponse>(
                service, METHODID_SAVE_OUTPUT)))
        .build();
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DataStoreServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getLoadInputMethod())
              .addMethod(getSaveOutputMethod())
              .build();
        }
      }
    }
    return result;
  }
}
