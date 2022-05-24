package com.cipek.model;

import com.cipek.constant.ClientConstants;
import io.grpc.CallCredentials;
import io.grpc.Metadata;

import java.util.concurrent.Executor;

public class SessionTokenCallCredential extends CallCredentials {

    private String jwtToken;

    public SessionTokenCallCredential(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
        executor.execute(
                () -> {
                    Metadata metadata = new Metadata();
                    metadata.put(ClientConstants.SESSION_TOKEN_KEY, jwtToken);
                    metadataApplier.apply(metadata);
//                    metadataApplier.fail(Status.UNAUTHENTICATED);
                }
        );
    }

    @Override
    public void thisUsesUnstableApi() {
        //TODO : Later
    }
}
