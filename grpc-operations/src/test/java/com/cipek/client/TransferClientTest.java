package com.cipek.client;

import com.cipek.grpc.model.proto.TransferRequest;
import com.cipek.grpc.model.proto.TransferServiceGrpc;
import com.cipek.model.TransferResponseStreamObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferClientTest {

    private TransferServiceGrpc.TransferServiceStub transferServiceStub;

    @BeforeAll
    public void setup() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8900)
                .usePlaintext().build();

        this.transferServiceStub = TransferServiceGrpc.newStub(managedChannel);
    }

    @Test
    public void test_transferBidirectional () throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<TransferRequest> transferRequestStreamObserver = transferServiceStub.transfer(new TransferResponseStreamObserver(latch));
        for (int i = 0; i < 100; i++) {
            TransferRequest request = TransferRequest.newBuilder()
                    .setFromAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setToAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setAmount(ThreadLocalRandom.current().nextInt(1, 21))
                    .build();
            transferRequestStreamObserver.onNext(request);
        }
        transferRequestStreamObserver.onCompleted();
        latch.await();
    }
}
