package com.github.nlatyshev.sbertech;

import com.github.nlatyshev.sbertech.model.Order;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

public class OrderStorage {
    private final Map<String, List<Order>> ordersByAssetType = new LinkedHashMap<>();

    public Optional<Order> pullMatchedTo(Order order) {
        List<Order> assetOrders = ordersByAssetType.getOrDefault(order.getAccountDetails().getAssetType(), Collections.<Order>emptyList());
        ListIterator<Order> it = assetOrders.listIterator();
        while (it.hasNext()) {
            Order candidate = it.next();
            if (order.getAmount() == -candidate.getAmount()
                    && order.getPrice() == candidate.getPrice()) {
                it.remove();
                return Optional.of(candidate);
            }
        }
        return Optional.empty();
    }

    public void store(final Order order) {
        ordersByAssetType.compute(order.getAccountDetails().getAssetType(), (assetType, assetOrders) -> {
            assetOrders = assetOrders != null ? assetOrders : new LinkedList<>();
            assetOrders.add(order);
            return assetOrders;
        });
    }
}
