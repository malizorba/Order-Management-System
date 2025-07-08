package com.example.userservice.Service.grpc;

import com.example.userproto.UserRequest;
import com.example.userproto.UserResponse;
import com.example.userproto.UserServiceGrpc;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Entity.UserEntity;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import static com.example.userservice.security.AuthInterceptor.USER_EMAIL_CTX_KEY;

@GrpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    @Override
    public void getUserByEmail(Empty request, StreamObserver<UserResponse> responseObserver) {
        String email = USER_EMAIL_CTX_KEY.get();

        if (email == null) {
            responseObserver.onError(Status.UNAUTHENTICATED
                    .withDescription("JWT geçersiz veya eksik").asRuntimeException());
            return;
        }

        // DB'den kullanıcıyı bul, yanıtla
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        UserResponse response = UserResponse.newBuilder()
                .setEmail(user.getEmail())
                .setFullName(user.getFullName())
                .setPhoneNumber(user.getPhoneNumber())
                .setPreferredChannel(com.example.userproto.NotificationChannel.valueOf(String.valueOf(user.getPreferredChannel())))
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
