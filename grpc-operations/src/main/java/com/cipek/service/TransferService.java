package com.cipek.service;

import com.cipek.grpc.model.proto.TransferRequest;
import com.cipek.grpc.model.proto.TransferResponse;
import com.cipek.grpc.model.proto.TransferServiceGrpc;
import com.cipek.model.TransferRequestStreamObserver;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        StreamObserver<TransferRequest> transferRequestStreamObserver = new TransferRequestStreamObserver(responseObserver);
        return transferRequestStreamObserver;
    }
}
