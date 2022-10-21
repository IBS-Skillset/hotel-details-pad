package com.hoteldetails.pad.interceptors;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;

import java.util.Objects;

@GrpcGlobalServerInterceptor
@Slf4j
public class ApiKeyAuthInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        log.info("Sever interceptor {}", serverCall.getMethodDescriptor());

        Metadata.Key<String> apiKeyMetadata = Metadata.Key.of("accesstoken", Metadata.ASCII_STRING_MARSHALLER);
        String apiKey = metadata.get(apiKeyMetadata);
        log.info("accessToken from client {}", apiKey);

        if (Objects.nonNull(apiKey) && apiKey.equals("valuetest")) {
            return serverCallHandler.startCall(serverCall, metadata);
        } else {
            Status status = Status.UNAUTHENTICATED.withDescription("Invalid access token");
            serverCall.close(status, metadata);
        }
        return new ServerCall.Listener<>() {
        };
    }
}
