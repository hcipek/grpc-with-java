package com.cipek.model;

import com.cipek.constant.ServerConstants;
import io.grpc.*;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;

public class CustomServerInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String token = metadata.get(ServerConstants.TOKEN);
        if (validate(token))
            return serverCallHandler.startCall(serverCall, metadata);
        Status status = Status.UNAUTHENTICATED.withDescription("Invalid/Expired Token");
        serverCall.close(status, metadata);
        return new ServerCall.Listener<ReqT>() {};
    }

    private boolean validate (String token) {
        return !StringUtil.isNullOrEmpty(token) && token.equals("client-ultimate-secret-token");
    }
}
