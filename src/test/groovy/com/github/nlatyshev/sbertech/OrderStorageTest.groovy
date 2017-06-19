package com.github.nlatyshev.sbertech

import com.github.nlatyshev.sbertech.model.AccountDetails
import com.github.nlatyshev.sbertech.model.Order
import spock.lang.Specification


class OrderStorageTest extends Specification {
    def storage = new OrderStorage()

    def "Take first coming opposite order from storage"() {
        when:
            storage.store(new Order(new AccountDetails('c1', 'a'), 1, 2))
            storage.store(new Order(new AccountDetails('c2', 'a'), 1, 2))
        then:
            storage.takeMatchedTo(new Order(new AccountDetails('c3', 'a'), -1, 2)) == Optional.of(new Order(new AccountDetails('c1', 'a'), 1, 2))
            storage.takeMatchedTo(new Order(new AccountDetails('c3', 'a'), -1, 2)) == Optional.of(new Order(new AccountDetails('c2', 'a'), 1, 2))
            storage.takeMatchedTo(new Order(new AccountDetails('c3', 'a'), -1, 2)) == Optional.empty()
    }

    def 'Return none if cannot find opposite order with the same product'() {
        given:
            storage.store(new Order(new AccountDetails('c1', 'a'), 1, 2))
        expect:
            storage.takeMatchedTo(new Order(new AccountDetails('c3', 'b'), -1, 2)) == Optional.empty()
            storage.takeMatchedTo(new Order(new AccountDetails('c3', 'a'), 1, 2)) == Optional.empty()
            storage.takeMatchedTo(new Order(new AccountDetails('c3', 'a'), -2, 2)) == Optional.empty()
            storage.takeMatchedTo(new Order(new AccountDetails('c3', 'a'), -1, 3)) == Optional.empty()
    }

}
