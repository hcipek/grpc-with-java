package com.cipek.model;

import com.cipek.grpc.model.proto.Account;
import com.cipek.grpc.model.proto.TransferRequest;
import com.cipek.grpc.model.proto.TransferResponse;
import com.cipek.grpc.model.proto.TransferStatus;
import com.cipek.repository.AccountRepository;
import io.grpc.stub.StreamObserver;

public class TransferRequestStreamObserver implements StreamObserver<TransferRequest> {

    private final AccountRepository accountRepository = new AccountRepository();
    private final StreamObserver<TransferResponse> transferResponseStreamObserver;

    public TransferRequestStreamObserver(StreamObserver<TransferResponse> transferResponseStreamObserver) {
        this.transferResponseStreamObserver = transferResponseStreamObserver;
    }

    @Override
    public void onNext(TransferRequest transferRequest) {
        int fromAccountNumber = transferRequest.getFromAccount();
        int fromAccountBalance = accountRepository.getBalance(fromAccountNumber);
        int toAccountNumber = transferRequest.getToAccount();
        int toAccountBalance = accountRepository.getBalance(toAccountNumber);
        int transferringAmount = transferRequest.getAmount();
        TransferStatus status = TransferStatus.FAILED;

        if (fromAccountBalance > transferringAmount && fromAccountNumber != toAccountNumber) {
            fromAccountBalance = accountRepository.withdrawal(fromAccountNumber, transferringAmount);
            toAccountBalance = accountRepository.deposit(toAccountNumber, transferringAmount);
            status = TransferStatus.SUCCESS;
        }

        Account fromAccount = Account.newBuilder()
                .setAccountNumber(fromAccountNumber)
                .setAmount(fromAccountBalance)
                .build();
        Account toAccount = Account.newBuilder()
                .setAccountNumber(toAccountNumber)
                .setAmount(toAccountBalance)
                .build();
        TransferResponse response = TransferResponse.newBuilder()
                .setStatus(status)
                .addAccounts(fromAccount)
                .addAccounts(toAccount)
                .build();
        transferResponseStreamObserver.onNext(response);
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onCompleted() {
        transferResponseStreamObserver.onCompleted();
    }
}
