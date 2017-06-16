package com.github.nlatyshev.sbertech.util;

import com.github.nlatyshev.sbertech.model.AccountDetails;
import com.github.nlatyshev.sbertech.model.Order;

import java.util.Arrays;

/**
 * @author nlatyshev on 16.06.2017.
 */
public class OrderParser {

    public Order parse(String[] items) {
        return new Order(new AccountDetails(items[0], items[2]),
                Integer.parseInt(items[3]), parsePrice(items));
    }

    private int parsePrice(String[] items) {
        String operationType = items[1];
        switch (operationType) {
            case "b":
                return Integer.parseInt(items[4]);
            case "s":
                return Integer.parseInt(items[4]);
        }
        throw new IllegalArgumentException(String.format("Unknown order type: %s in %s", operationType, Arrays.toString(items)));

    }
}
