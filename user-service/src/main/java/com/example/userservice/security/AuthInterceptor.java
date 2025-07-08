package com.example.userservice.security;

import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements ServerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public static final Context.Key<String> USER_EMAIL_CTX_KEY = Context.key("user-email");

    private static final Metadata.Key<String> AUTHORIZATION_KEY =
            Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String authHeader = metadata.get(AUTHORIZATION_KEY);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String email = jwtTokenProvider.getEmailFromToken(token); // token'dan email'i al
                Context ctx = Context.current().withValue(USER_EMAIL_CTX_KEY, email);
                return Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler);
            } catch (Exception e) {
                log.warn("JWT doğrulama başarısız: {}", e.getMessage());
            }
        }

        return serverCallHandler.startCall(serverCall, metadata);
    }
}
