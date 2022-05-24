package com.cipek.service;

import com.cipek.model.DepositStreamingRequest;
import com.cipek.loadbalance.model.proto.*;
import com.cipek.repository.AccountRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    private AccountRepository accountRepository = new AccountRepository();

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        System.out.println("Recieved : " + request.getAccountNumber());
        int accountNumber = request.getAccountNumber();
        int accountBalance = accountRepository.getBalance(accountNumber);
        Balance balance = Balance.newBuilder()
                .setAmount(accountBalance)
                .build();
        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

    @Override
    public void withdrawal(WithdrawalRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount();

        int balance = accountRepository.getBalance(accountNumber);

        if (balance < amount) {
            Status status = Status.FAILED_PRECONDITION.withDescription("Not enough balance for withdrawal");
            responseObserver.onError(status.asRuntimeException());
            responseObserver.onCompleted();
            return;
        }

        for (int i = 0; i < amount/10; i++) {
            Money money = Money.newBuilder()
                    .setAmount(10)
                    .build();
            responseObserver.onNext(money);
            accountRepository.withdrawal(accountNumber, 10);
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DepositRequest> deposit(StreamObserver<Balance> responseObserver) {
        return new DepositStreamingRequest(responseObserver);
    }
}
