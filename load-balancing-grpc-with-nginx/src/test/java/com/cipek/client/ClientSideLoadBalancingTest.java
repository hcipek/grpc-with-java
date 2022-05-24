package com.cipek.client;

import com.cipek.loadbalance.model.proto.Balance;
import com.cipek.loadbalance.model.proto.BalanceCheckRequest;
import com.cipek.loadbalance.model.proto.BankServiceGrpc;
import com.cipek.model.TestNameResolverProvider;
import com.cipek.registry.ServiceRegistry;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.NameResolverRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientSideLoadBalancingTest {

    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setup () {

//        String jwtToken = authenticationService.authenticate(userName, password);

        ServiceRegistry.register("bank-service", Arrays.asList("localhost:7001", "localhost:7002"));
        NameResolverRegistry.getDefaultRegistry()
                .register(new TestNameResolverProvider());
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forTarget("http://bank-service")
//                .intercept(new CustomInterceptor())
//                .intercept(MetadataUtils.newAttachHeadersInterceptor(ClientConstants.getClientMetadata()))
//                .intercept(new CustomCallCredentialInterceptor(jwtToken))
                .defaultLoadBalancingPolicy("round_robin")
                .usePlaintext().build();

        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);
    }

    @Test
    public void test_loadBalancingGetBalance () {
        for (int i = 1; i<100; i++) {
            BalanceCheckRequest request = BalanceCheckRequest.newBuilder().setAccountNumber(i%10 + 1)
                    .build();
            Balance balance = bankServiceBlockingStub.getBalance(request);
            System.out.println("Received : " + balance.getAmount());
        }
    }

}
