package com.cipek.constant;

import io.grpc.Metadata;

public class ClientConstants {

    private static final Metadata METADATA = new Metadata();
    public static final Metadata.Key SESSION_TOKEN_KEY = Metadata.Key.of("session-token", Metadata.ASCII_STRING_MARSHALLER);

    static {
        Metadata.Key metadataKey = Metadata.Key.of("client-token", Metadata.ASCII_STRING_MARSHALLER);
        String clientSecret = "client-ultimate-secret-token";
        METADATA.put(metadataKey, clientSecret);
    }

    public static Metadata getClientMetadata () {
        return METADATA;
    }
}
