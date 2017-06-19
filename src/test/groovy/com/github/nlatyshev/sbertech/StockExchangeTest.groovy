package com.github.nlatyshev.sbertech

import com.github.nlatyshev.sbertech.model.Account
import com.github.nlatyshev.sbertech.model.AccountDetails
import com.github.nlatyshev.sbertech.model.Order
import spock.lang.Specification

class StockExchangeTest extends Specification {
    def accounts = Mock(AccountStorage)
    def orders = Mock(OrderStorage)
    def stockExchange = new StockExchange(accounts, orders)

    def 'Execute order: C1 -assets-> C2, C2 -$-> C1'() {
        setup:
            def order = new Order(new AccountDetails('C1', 'A'), 2, 3)
            def C1$ = new Account(new AccountDetails('C1', '$'), 30)
            def C1A = new Account(new AccountDetails('C1', 'A'), 5)
            accounts.findSettlementAccount('C1') >> C1$
            accounts.findAccount(new AccountDetails('C1', 'A')) >> C1A

            def contraryOrder = new Order(new AccountDetails('C2', 'A'), -2, 3)
            def C2$ = new Account(new AccountDetails('C2', '$'), 40)
            def C2A = new Account(new AccountDetails('C2', 'A'), 15)
            accounts.findSettlementAccount('C2') >> C2$
            accounts.findAccount(new AccountDetails('C2', 'A')) >> C2A

            orders.takeMatchedTo(order) >> Optional.of(contraryOrder)
        when:
            stockExchange.execute(order)
        then:
            C1$.amount == 24
            C1A.amount == 7

            C2$.amount == 46
            C2A.amount == 13
    }

    def 'Do nothing if order client has not got appropriate account'() {
        setup:
            def order = new Order(new AccountDetails('C1', 'A'), 2, 3)
            def C1$ = new Account(new AccountDetails('C1', '$'), 30)
            def C1A = new Account(new AccountDetails('C1', 'A'), 5)
            accounts.findAccount(new AccountDetails('C1', 'A')) >> null
        when:
            stockExchange.execute(order)
        then:
            C1$.amount == 30
            C1A.amount == 5
            0 * orders.store(order);
    }

    def 'Memorize order to process in the future if cannot find matched one'() {
        setup:
           def order = new Order(new AccountDetails('C1', 'A'), 2, 3)
           def C1A = new Account(new AccountDetails('C1', 'A'), 5)
           accounts.findAccount(new AccountDetails('C1', 'A')) >> C1A

            orders.takeMatchedTo(order) >> Optional.empty()
        when:
            stockExchange.execute(order)
        then:
            C1A.amount == 5
            1 * orders.store(order);

    }
}
