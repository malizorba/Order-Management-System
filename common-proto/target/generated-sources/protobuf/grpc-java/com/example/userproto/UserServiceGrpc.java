package com.example.userproto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.61.0)",
    comments = "Source: UserService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class UserServiceGrpc {

  private UserServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "user.UserService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.example.userproto.UserResponse> getGetUserByTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUserByToken",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.example.userproto.UserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.example.userproto.UserResponse> getGetUserByTokenMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.example.userproto.UserResponse> getGetUserByTokenMethod;
    if ((getGetUserByTokenMethod = UserServiceGrpc.getGetUserByTokenMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getGetUserByTokenMethod = UserServiceGrpc.getGetUserByTokenMethod) == null) {
          UserServiceGrpc.getGetUserByTokenMethod = getGetUserByTokenMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.example.userproto.UserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUserByToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.userproto.UserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("GetUserByToken"))
              .build();
        }
      }
    }
    return getGetUserByTokenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.userproto.FavoriteProductRequest,
      com.example.userproto.UserListResponse> getGetUsersByFavoriteProductMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUsersByFavoriteProduct",
      requestType = com.example.userproto.FavoriteProductRequest.class,
      responseType = com.example.userproto.UserListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.userproto.FavoriteProductRequest,
      com.example.userproto.UserListResponse> getGetUsersByFavoriteProductMethod() {
    io.grpc.MethodDescriptor<com.example.userproto.FavoriteProductRequest, com.example.userproto.UserListResponse> getGetUsersByFavoriteProductMethod;
    if ((getGetUsersByFavoriteProductMethod = UserServiceGrpc.getGetUsersByFavoriteProductMethod) == null) {
      synchronized (UserServiceGrpc.class) {
        if ((getGetUsersByFavoriteProductMethod = UserServiceGrpc.getGetUsersByFavoriteProductMethod) == null) {
          UserServiceGrpc.getGetUsersByFavoriteProductMethod = getGetUsersByFavoriteProductMethod =
              io.grpc.MethodDescriptor.<com.example.userproto.FavoriteProductRequest, com.example.userproto.UserListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUsersByFavoriteProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.userproto.FavoriteProductRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.userproto.UserListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserServiceMethodDescriptorSupplier("GetUsersByFavoriteProduct"))
              .build();
        }
      }
    }
    return getGetUsersByFavoriteProductMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserServiceStub>() {
        @java.lang.Override
        public UserServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserServiceStub(channel, callOptions);
        }
      };
    return UserServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserServiceBlockingStub>() {
        @java.lang.Override
        public UserServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserServiceBlockingStub(channel, callOptions);
        }
      };
    return UserServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserServiceFutureStub>() {
        @java.lang.Override
        public UserServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserServiceFutureStub(channel, callOptions);
        }
      };
    return UserServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getUserByToken(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.example.userproto.UserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUserByTokenMethod(), responseObserver);
    }

    /**
     * <pre>
     * Yeni eklenen metod: FavoriteProductId vererek kullanıcı listesi alma
     * </pre>
     */
    default void getUsersByFavoriteProduct(com.example.userproto.FavoriteProductRequest request,
        io.grpc.stub.StreamObserver<com.example.userproto.UserListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUsersByFavoriteProductMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service UserService.
   */
  public static abstract class UserServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return UserServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service UserService.
   */
  public static final class UserServiceStub
      extends io.grpc.stub.AbstractAsyncStub<UserServiceStub> {
    private UserServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserServiceStub(channel, callOptions);
    }

    /**
     */
    public void getUserByToken(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.example.userproto.UserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUserByTokenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Yeni eklenen metod: FavoriteProductId vererek kullanıcı listesi alma
     * </pre>
     */
    public void getUsersByFavoriteProduct(com.example.userproto.FavoriteProductRequest request,
        io.grpc.stub.StreamObserver<com.example.userproto.UserListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUsersByFavoriteProductMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service UserService.
   */
  public static final class UserServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<UserServiceBlockingStub> {
    private UserServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.userproto.UserResponse getUserByToken(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUserByTokenMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Yeni eklenen metod: FavoriteProductId vererek kullanıcı listesi alma
     * </pre>
     */
    public com.example.userproto.UserListResponse getUsersByFavoriteProduct(com.example.userproto.FavoriteProductRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUsersByFavoriteProductMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service UserService.
   */
  public static final class UserServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<UserServiceFutureStub> {
    private UserServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.userproto.UserResponse> getUserByToken(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUserByTokenMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Yeni eklenen metod: FavoriteProductId vererek kullanıcı listesi alma
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.userproto.UserListResponse> getUsersByFavoriteProduct(
        com.example.userproto.FavoriteProductRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUsersByFavoriteProductMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_USER_BY_TOKEN = 0;
  private static final int METHODID_GET_USERS_BY_FAVORITE_PRODUCT = 1;

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
        case METHODID_GET_USER_BY_TOKEN:
          serviceImpl.getUserByToken((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<com.example.userproto.UserResponse>) responseObserver);
          break;
        case METHODID_GET_USERS_BY_FAVORITE_PRODUCT:
          serviceImpl.getUsersByFavoriteProduct((com.example.userproto.FavoriteProductRequest) request,
              (io.grpc.stub.StreamObserver<com.example.userproto.UserListResponse>) responseObserver);
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
          getGetUserByTokenMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              com.example.userproto.UserResponse>(
                service, METHODID_GET_USER_BY_TOKEN)))
        .addMethod(
          getGetUsersByFavoriteProductMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.example.userproto.FavoriteProductRequest,
              com.example.userproto.UserListResponse>(
                service, METHODID_GET_USERS_BY_FAVORITE_PRODUCT)))
        .build();
  }

  private static abstract class UserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.userproto.UserServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UserService");
    }
  }

  private static final class UserServiceFileDescriptorSupplier
      extends UserServiceBaseDescriptorSupplier {
    UserServiceFileDescriptorSupplier() {}
  }

  private static final class UserServiceMethodDescriptorSupplier
      extends UserServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    UserServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (UserServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserServiceFileDescriptorSupplier())
              .addMethod(getGetUserByTokenMethod())
              .addMethod(getGetUsersByFavoriteProductMethod())
              .build();
        }
      }
    }
    return result;
  }
}
