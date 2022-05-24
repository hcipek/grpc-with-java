package com.cipek.model;

import com.cipek.registry.ServiceRegistry;
import io.grpc.NameResolver;

public class TestNameResolver extends NameResolver {

    private final String service;

    public TestNameResolver(String service) {
        this.service = service;
    }

    @Override
    public String getServiceAuthority() {
        return "test";
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void refresh () {
        super.refresh();
    }

    @Override
    public void start(Listener2 listener2) {
        ResolutionResult resolutionResult = ResolutionResult.newBuilder()
                .setAddresses(ServiceRegistry.getInstances(service))
                .build();
        listener2.onResult(resolutionResult);
    }
}
