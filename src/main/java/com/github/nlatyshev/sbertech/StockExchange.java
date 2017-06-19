package com.github.nlatyshev.sbertech;

import com.github.nlatyshev.sbertech.model.Account;
import com.github.nlatyshev.sbertech.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class StockExchange {
    private static final Logger log = LoggerFactory.getLogger(StockExchange.class);
    private AccountStorage accounts;
    private OrderStorage orderStorage;

    public StockExchange(AccountStorage accounts, OrderStorage orderStorage) {
        this.accounts = accounts;
        this.orderStorage = orderStorage;
    }

    public void execute(Order order) {
        Account account = accounts.findAccount(order.getAccountDetails());
        if (account != null) {
            doExecute(order);
        } else {
            log.error("Cannot execute order {}: has no account ", order, order.getAccountDetails());
        }
    }

    private void doExecute(Order order) {
        Optional<Order> contraryOrder = orderStorage.takeMatchedTo(order);
        if (contraryOrder.isPresent()) {
            transfer(order.getAmount(),
                    findAccount(contraryOrder.get()), findAccount(order));
            transfer(order.getAmount() * order.getPrice(),
                    findSettlementAccount(order), findSettlementAccount(contraryOrder.get()));
        } else {
            orderStorage.store(order);
        }
    }

    private void transfer(int value, Account from, Account to) {
        from.setAmount(from.getAmount() - value);
        to.setAmount(to.getAmount() + value);
    }

    private Account findAccount(Order order) {
        return accounts.findAccount(order.getAccountDetails());
    }

    private Account findSettlementAccount(Order order) {
        return accounts.findSettlementAccount(order.getAccountDetails().getClient());
    }
}
