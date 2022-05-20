package com.cipek.service;

import com.cipek.repository.AccountRepository;
import com.cipek.unary.model.proto.Balance;
import com.cipek.unary.model.proto.BalanceCheckRequest;
import com.cipek.unary.model.proto.BankServiceGrpc;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    private AccountRepository accountRepository = new AccountRepository();

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int accountBalance = accountRepository.getBalance(accountNumber);
        Balance balance = Balance.newBuilder()
                .setAmount(accountBalance)
                .build();
        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }
}
