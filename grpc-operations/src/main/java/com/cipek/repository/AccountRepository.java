package com.cipek.repository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountRepository {

    private static final Map<Integer, Integer> BANK_DATABASE = IntStream.rangeClosed(1, 10)
            .boxed()
            .collect(Collectors.toMap(Function.identity(), v -> v*10));

    public int getBalance (int accountId) {
        return BANK_DATABASE.get(accountId);
    }

    public int deposit (int accountId, int amount) {
        return BANK_DATABASE.computeIfPresent(accountId, (k, v) -> v + amount);
    }

    public int withdrawal (int accountId, int amount) {
        //This is for testing, in reality, we need to check that if we have enough balance to withdrawal that amount from our account.
        return BANK_DATABASE.computeIfPresent(accountId, (k, v) -> v - amount);
    }



}
