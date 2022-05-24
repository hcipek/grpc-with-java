package com.cipek.client;

import com.cipek.loadbalance.model.proto.Balance;
import com.cipek.loadbalance.model.proto.BalanceCheckRequest;
import com.cipek.loadbalance.model.proto.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientLoadBalancingTest {

    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;

    @BeforeAll
    public void setup () {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8585)
                .usePlaintext().build();

        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
    }

    @Test
    public void test_loadBalancingGetBalance () {
        for (int i = 1; i<11; i++) {
            BalanceCheckRequest request = BalanceCheckRequest.newBuilder().setAccountNumber(i)
                    .build();
            Balance balance = bankServiceBlockingStub.getBalance(request);
            System.out.println("Received : " + balance.getAmount());
        }
    }
}
