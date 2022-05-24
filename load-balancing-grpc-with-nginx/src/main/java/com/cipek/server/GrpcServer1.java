package com.cipek.server;

import com.cipek.service.BankService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer1 {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(7001)
                .addService(new BankService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
