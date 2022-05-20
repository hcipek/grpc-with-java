package com.cipek.client;

import com.cipek.unary.model.proto.Balance;
import com.cipek.unary.model.proto.BalanceCheckRequest;
import com.cipek.unary.model.proto.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;

    @BeforeAll
    public void setup() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8900)
                .usePlaintext().build();

        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
    }

    @Test
    public void test_getBalance() {
        BalanceCheckRequest request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(10)
                .build();
        Balance balance = bankServiceBlockingStub.getBalance(request);
        System.out.println(balance);
    }
}
