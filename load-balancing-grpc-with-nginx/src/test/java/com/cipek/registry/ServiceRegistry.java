package com.cipek.registry;

import io.grpc.EquivalentAddressGroup;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceRegistry {

    private static final Map<String, List<EquivalentAddressGroup>> MAP = new HashMap<>();

    public static void register(String service, List<String> instances) {
        List<EquivalentAddressGroup> addressGroupList = instances.stream()
                .map(URI::create)
                .map(u -> new InetSocketAddress(u.getScheme(), Integer.valueOf(u.getSchemeSpecificPart())))
                .map(EquivalentAddressGroup::new)
                .collect(Collectors.toList());

        MAP.put(service, addressGroupList);
    }

    public static List<EquivalentAddressGroup> getInstances (String serviceName) {
        return MAP.get(serviceName);
    }
}
