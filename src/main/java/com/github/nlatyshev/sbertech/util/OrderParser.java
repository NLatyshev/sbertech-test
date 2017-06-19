package com.github.nlatyshev.sbertech.util;

import com.github.nlatyshev.sbertech.model.AccountDetails;
import com.github.nlatyshev.sbertech.model.Order;

import java.util.Arrays;

public class OrderParser {

    public Order parse(String[] items) {
        if (items.length == 5) {
            return new Order(new AccountDetails(items[0], items[2]),
                    parseAmount(items), Integer.parseInt(items[4])) ;
        }
        throw new IllegalArgumentException(String.format("Cannot create client accounts from %s: expected 5 items, but got %s",
                        Arrays.toString(items), items.length));
    }

    private int parseAmount(String[] items) {
        String operationType = items[1];
        switch (operationType) {
            case "b":
                return Integer.parseInt(items[3]);
            case "s":
                return -Integer.parseInt(items[3]);
        }
        throw new IllegalArgumentException(String.format("Unknown order type: %s in %s", operationType, Arrays.toString(items)));

    }
}
