package com.cipek.server;

import com.cipek.service.GameService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class SnakeAndLaddersGrpcServer {

    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(9001)
                .addService(new GameService())
                .build();

        try {
            server.start();
            server.awaitTermination();
        } catch (IOException e) {
            System.out.println("Server Start failed");
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Server Interrupted.");
            System.out.println(e.getMessage());
        }
    }
}
