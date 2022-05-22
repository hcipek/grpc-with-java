package com.cipek.model;

import com.cipek.grpc.model.proto.TransferResponse;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class TransferResponseStreamObserver implements StreamObserver<TransferResponse> {

    private final CountDownLatch latch;

    public TransferResponseStreamObserver(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(TransferResponse transferResponse) {
        System.out.println(transferResponse.getStatus());
        transferResponse.getAccountsList().forEach(e -> System.out.println(e.getAccountNumber() + " : amount : " + e.getAmount()));
        System.out.println("!----------------------------!");
    }

    @Override
    public void onError(Throwable throwable) {
        latch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println("All transfers done");
        latch.countDown();
    }
}
