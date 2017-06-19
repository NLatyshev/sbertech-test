package com.github.nlatyshev.sbertech.dao

import com.github.nlatyshev.sbertech.ByteArrayResource
import com.github.nlatyshev.sbertech.TestSupport
import com.github.nlatyshev.sbertech.model.AccountDetails
import com.github.nlatyshev.sbertech.model.Order
import spock.lang.Specification

import java.util.function.Consumer
import java.util.function.Function

class OrderImporterTest extends Specification implements TestSupport{
    Function<String, Order> orderParser = Mock(Function)
    def importer = new OrderImporter(orderParser)

    def 'Read, parse and delegate to handler orders'() {
        setup:
            def resource = new ByteArrayResource("o1${lineSep}o2")
            Consumer<Order> handler = Mock(Consumer)
            orderParser.apply('o1') >> new Order(new AccountDetails('c1', '$'), 1, 2)
            orderParser.apply('o2') >> new Order(new AccountDetails('c2', 'A'), 3, 4)
        when:
            importer.importOrders(resource, handler)
        then:
            1 * handler.accept(new Order(new AccountDetails('c1', '$'), 1, 2))
            1 * handler.accept(new Order(new AccountDetails('c2', 'A'), 3, 4))

    }
}
