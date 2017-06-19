package com.github.nlatyshev.sbertech.dao;

import com.github.nlatyshev.sbertech.model.Order;
import com.github.nlatyshev.sbertech.util.FlatFileReader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class OrderImporter {
    private Function<String, Order> orderParser;

    public OrderImporter(Function<String, Order> orderParser) {
        this.orderParser = orderParser;
    }

    public void importOrders(Resource resource, Consumer<Order> handler) {
        try {
            new FlatFileReader<>(resource, orderParser).forEach(handler);
        } catch (IOException e) {
            throw new RuntimeException("Cannot import orders", e);
        }
    }
}
