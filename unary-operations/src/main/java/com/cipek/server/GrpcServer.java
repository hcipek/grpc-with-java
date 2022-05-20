package com.cipek.server;

import com.cipek.service.BankService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8900)
                .addService(new BankService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
