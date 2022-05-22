package com.cipek.client;

import com.cipek.game.proto.DiceRequest;
import com.cipek.game.proto.GameServiceGrpc;
import com.cipek.model.GameResponseStreamObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameClientTest {

    private GameServiceGrpc.GameServiceStub gameServiceStub;

    @BeforeAll
    public void setup() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9001)
                .usePlaintext().build();

        this.gameServiceStub = GameServiceGrpc.newStub(managedChannel);
    }

    @Test
    public void test_snakeAndLaddersGame () throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        GameResponseStreamObserver responseStreamObserver = new GameResponseStreamObserver(latch);
        StreamObserver<DiceRequest> requestStreamObserver = gameServiceStub.rollTheDice(responseStreamObserver);
        responseStreamObserver.setRequestStreamObserver(requestStreamObserver);
        responseStreamObserver.startTheGame();
        latch.await();
    }
}
