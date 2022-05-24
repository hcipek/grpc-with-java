package com.cipek.model;

import io.grpc.*;

public class CustomCallCredentialInterceptor implements ClientInterceptor {

    private final String token;

    public CustomCallCredentialInterceptor(String token) {
        this.token = token;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        return channel.newCall(methodDescriptor, callOptions.withCallCredentials(new SessionTokenCallCredential(this.token)));
    }
}
