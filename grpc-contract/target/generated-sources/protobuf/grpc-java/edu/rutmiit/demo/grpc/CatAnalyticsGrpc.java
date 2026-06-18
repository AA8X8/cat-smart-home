package edu.rutmiit.demo.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.66.0)",
    comments = "Source: cat_analytics.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CatAnalyticsGrpc {

  private CatAnalyticsGrpc() {}

  public static final java.lang.String SERVICE_NAME = "catanalytics.CatAnalytics";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<edu.rutmiit.demo.grpc.AnalyzeCatRequest,
      edu.rutmiit.demo.grpc.CatAnalysisResponse> getAnalyzeCatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AnalyzeCat",
      requestType = edu.rutmiit.demo.grpc.AnalyzeCatRequest.class,
      responseType = edu.rutmiit.demo.grpc.CatAnalysisResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<edu.rutmiit.demo.grpc.AnalyzeCatRequest,
      edu.rutmiit.demo.grpc.CatAnalysisResponse> getAnalyzeCatMethod() {
    io.grpc.MethodDescriptor<edu.rutmiit.demo.grpc.AnalyzeCatRequest, edu.rutmiit.demo.grpc.CatAnalysisResponse> getAnalyzeCatMethod;
    if ((getAnalyzeCatMethod = CatAnalyticsGrpc.getAnalyzeCatMethod) == null) {
      synchronized (CatAnalyticsGrpc.class) {
        if ((getAnalyzeCatMethod = CatAnalyticsGrpc.getAnalyzeCatMethod) == null) {
          CatAnalyticsGrpc.getAnalyzeCatMethod = getAnalyzeCatMethod =
              io.grpc.MethodDescriptor.<edu.rutmiit.demo.grpc.AnalyzeCatRequest, edu.rutmiit.demo.grpc.CatAnalysisResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AnalyzeCat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  edu.rutmiit.demo.grpc.AnalyzeCatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  edu.rutmiit.demo.grpc.CatAnalysisResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CatAnalyticsMethodDescriptorSupplier("AnalyzeCat"))
              .build();
        }
      }
    }
    return getAnalyzeCatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<edu.rutmiit.demo.grpc.PredictActivityRequest,
      edu.rutmiit.demo.grpc.PredictActivityResponse> getPredictActivityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PredictActivity",
      requestType = edu.rutmiit.demo.grpc.PredictActivityRequest.class,
      responseType = edu.rutmiit.demo.grpc.PredictActivityResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<edu.rutmiit.demo.grpc.PredictActivityRequest,
      edu.rutmiit.demo.grpc.PredictActivityResponse> getPredictActivityMethod() {
    io.grpc.MethodDescriptor<edu.rutmiit.demo.grpc.PredictActivityRequest, edu.rutmiit.demo.grpc.PredictActivityResponse> getPredictActivityMethod;
    if ((getPredictActivityMethod = CatAnalyticsGrpc.getPredictActivityMethod) == null) {
      synchronized (CatAnalyticsGrpc.class) {
        if ((getPredictActivityMethod = CatAnalyticsGrpc.getPredictActivityMethod) == null) {
          CatAnalyticsGrpc.getPredictActivityMethod = getPredictActivityMethod =
              io.grpc.MethodDescriptor.<edu.rutmiit.demo.grpc.PredictActivityRequest, edu.rutmiit.demo.grpc.PredictActivityResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PredictActivity"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  edu.rutmiit.demo.grpc.PredictActivityRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  edu.rutmiit.demo.grpc.PredictActivityResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CatAnalyticsMethodDescriptorSupplier("PredictActivity"))
              .build();
        }
      }
    }
    return getPredictActivityMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CatAnalyticsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CatAnalyticsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CatAnalyticsStub>() {
        @java.lang.Override
        public CatAnalyticsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CatAnalyticsStub(channel, callOptions);
        }
      };
    return CatAnalyticsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CatAnalyticsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CatAnalyticsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CatAnalyticsBlockingStub>() {
        @java.lang.Override
        public CatAnalyticsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CatAnalyticsBlockingStub(channel, callOptions);
        }
      };
    return CatAnalyticsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CatAnalyticsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CatAnalyticsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CatAnalyticsFutureStub>() {
        @java.lang.Override
        public CatAnalyticsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CatAnalyticsFutureStub(channel, callOptions);
        }
      };
    return CatAnalyticsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void analyzeCat(edu.rutmiit.demo.grpc.AnalyzeCatRequest request,
        io.grpc.stub.StreamObserver<edu.rutmiit.demo.grpc.CatAnalysisResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAnalyzeCatMethod(), responseObserver);
    }

    /**
     */
    default void predictActivity(edu.rutmiit.demo.grpc.PredictActivityRequest request,
        io.grpc.stub.StreamObserver<edu.rutmiit.demo.grpc.PredictActivityResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPredictActivityMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CatAnalytics.
   */
  public static abstract class CatAnalyticsImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CatAnalyticsGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CatAnalytics.
   */
  public static final class CatAnalyticsStub
      extends io.grpc.stub.AbstractAsyncStub<CatAnalyticsStub> {
    private CatAnalyticsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatAnalyticsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CatAnalyticsStub(channel, callOptions);
    }

    /**
     */
    public void analyzeCat(edu.rutmiit.demo.grpc.AnalyzeCatRequest request,
        io.grpc.stub.StreamObserver<edu.rutmiit.demo.grpc.CatAnalysisResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAnalyzeCatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void predictActivity(edu.rutmiit.demo.grpc.PredictActivityRequest request,
        io.grpc.stub.StreamObserver<edu.rutmiit.demo.grpc.PredictActivityResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPredictActivityMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CatAnalytics.
   */
  public static final class CatAnalyticsBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CatAnalyticsBlockingStub> {
    private CatAnalyticsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatAnalyticsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CatAnalyticsBlockingStub(channel, callOptions);
    }

    /**
     */
    public edu.rutmiit.demo.grpc.CatAnalysisResponse analyzeCat(edu.rutmiit.demo.grpc.AnalyzeCatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAnalyzeCatMethod(), getCallOptions(), request);
    }

    /**
     */
    public edu.rutmiit.demo.grpc.PredictActivityResponse predictActivity(edu.rutmiit.demo.grpc.PredictActivityRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPredictActivityMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CatAnalytics.
   */
  public static final class CatAnalyticsFutureStub
      extends io.grpc.stub.AbstractFutureStub<CatAnalyticsFutureStub> {
    private CatAnalyticsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CatAnalyticsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CatAnalyticsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<edu.rutmiit.demo.grpc.CatAnalysisResponse> analyzeCat(
        edu.rutmiit.demo.grpc.AnalyzeCatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAnalyzeCatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<edu.rutmiit.demo.grpc.PredictActivityResponse> predictActivity(
        edu.rutmiit.demo.grpc.PredictActivityRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPredictActivityMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ANALYZE_CAT = 0;
  private static final int METHODID_PREDICT_ACTIVITY = 1;

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
        case METHODID_ANALYZE_CAT:
          serviceImpl.analyzeCat((edu.rutmiit.demo.grpc.AnalyzeCatRequest) request,
              (io.grpc.stub.StreamObserver<edu.rutmiit.demo.grpc.CatAnalysisResponse>) responseObserver);
          break;
        case METHODID_PREDICT_ACTIVITY:
          serviceImpl.predictActivity((edu.rutmiit.demo.grpc.PredictActivityRequest) request,
              (io.grpc.stub.StreamObserver<edu.rutmiit.demo.grpc.PredictActivityResponse>) responseObserver);
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
          getAnalyzeCatMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              edu.rutmiit.demo.grpc.AnalyzeCatRequest,
              edu.rutmiit.demo.grpc.CatAnalysisResponse>(
                service, METHODID_ANALYZE_CAT)))
        .addMethod(
          getPredictActivityMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              edu.rutmiit.demo.grpc.PredictActivityRequest,
              edu.rutmiit.demo.grpc.PredictActivityResponse>(
                service, METHODID_PREDICT_ACTIVITY)))
        .build();
  }

  private static abstract class CatAnalyticsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CatAnalyticsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return edu.rutmiit.demo.grpc.CatAnalyticsOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CatAnalytics");
    }
  }

  private static final class CatAnalyticsFileDescriptorSupplier
      extends CatAnalyticsBaseDescriptorSupplier {
    CatAnalyticsFileDescriptorSupplier() {}
  }

  private static final class CatAnalyticsMethodDescriptorSupplier
      extends CatAnalyticsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CatAnalyticsMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CatAnalyticsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CatAnalyticsFileDescriptorSupplier())
              .addMethod(getAnalyzeCatMethod())
              .addMethod(getPredictActivityMethod())
              .build();
        }
      }
    }
    return result;
  }
}
