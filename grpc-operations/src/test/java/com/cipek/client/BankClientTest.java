package com.cipek.client;

import com.cipek.model.BalanceStreamObserver;
import com.cipek.model.MoneyStreamingResponse;
import com.cipek.unary.model.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setup() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8900)
                .usePlaintext().build();

        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);
    }

    @Test
    public void test_getBalance() {
        BalanceCheckRequest request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(9)
                .build();
        Balance balance = bankServiceBlockingStub.getBalance(request);
        System.out.println(balance);
    }

    @Test
    public void test_withdrawal() {
        WithdrawalRequest request = WithdrawalRequest.newBuilder()
                .setAccountNumber(6)
                .setAmount(30)
                .build();
        bankServiceBlockingStub.withdrawal(request)
                .forEachRemaining(e -> System.out.println(e.getAmount()));

    }

    @Test
    public void test_withdrawalAsync() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        WithdrawalRequest request = WithdrawalRequest.newBuilder()
                .setAccountNumber(10)
                .setAmount(30)
                .build();
        bankServiceStub.withdrawal(request, new MoneyStreamingResponse(latch));
        latch.await();
    }

    @Test
    public void test_deposit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<DepositRequest> requestStreamObserver = bankServiceStub.deposit(new BalanceStreamObserver(latch));
        for(int i = 0; i < 10; i++) {
            DepositRequest depositRequest = DepositRequest.newBuilder()
                    .setAccountNumber(7)
                    .setAmount(10)
                    .build();
            requestStreamObserver.onNext(depositRequest);
        }
        requestStreamObserver.onCompleted();
        latch.await();
    }
}
