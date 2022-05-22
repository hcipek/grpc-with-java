package com.cipek.model;

import com.cipek.repository.AccountRepository;
import com.cipek.grpc.model.proto.Balance;
import com.cipek.grpc.model.proto.DepositRequest;
import io.grpc.stub.StreamObserver;

public class DepositStreamingRequest implements StreamObserver<DepositRequest> {

    private StreamObserver<Balance> balanceStreamObserver;
    private AccountRepository accountRepository = new AccountRepository();
    private int accountBalance;

    public DepositStreamingRequest (StreamObserver<Balance> streamObserver) {
        balanceStreamObserver = streamObserver;
    }

    @Override
    public void onNext(DepositRequest depositRequest) {
        int accountNumber = depositRequest.getAccountNumber();
        int amount = depositRequest.getAmount();
        accountBalance = accountRepository.deposit(accountNumber, amount);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        Balance balance = Balance.newBuilder()
                .setAmount(accountBalance)
                .build();
        balanceStreamObserver.onNext(balance);
        balanceStreamObserver.onCompleted();
    }
}
