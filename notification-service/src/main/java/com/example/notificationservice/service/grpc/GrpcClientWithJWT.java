package com.example.notificationservice.service.grpc;

import com.example.userproto.UserRequest;
import com.example.userproto.UserResponse;
import com.example.userproto.UserServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcClientWithJWT {

    private final ManagedChannel userServiceChannel;

    public UserResponse getUserByToken(String jwtToken) {
        // Metadata oluştur
        Metadata metadata = new Metadata();
        Metadata.Key<String> AUTH_HEADER = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(AUTH_HEADER, "Bearer " + jwtToken);

        // Stub + Interceptor
        UserServiceGrpc.UserServiceBlockingStub stubWithJwt =
                UserServiceGrpc.newBlockingStub(userServiceChannel)
                        .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));

        // gRPC çağrısı
        return stubWithJwt.getUserByToken(Empty.getDefaultInstance());
    }
}
