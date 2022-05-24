package com.cipek.model;

import io.grpc.*;

import java.util.concurrent.TimeUnit;

public class CustomInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
//        callOptions.withAuthority();
//        callOptions.withDeadline();
//        callOptions.withCallCredentials();
//        callOptions.withCompression();
//        callOptions.withOption();
//        callOptions.withDeadlineAfter();
//        callOptions.withExecutor();
//        callOptions.withMaxInboundMessageSize();
//        callOptions.withMaxOutboundMessageSize();
//        callOptions.withoutWaitForReady();
//        callOptions.withStreamTracerFactory();
//        callOptions.withWaitForReady();
        return channel.newCall(methodDescriptor, callOptions.withDeadline(Deadline.after(10, TimeUnit.SECONDS)));
    }
}
