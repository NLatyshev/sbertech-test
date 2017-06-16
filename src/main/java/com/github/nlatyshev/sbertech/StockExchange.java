package com.github.nlatyshev.sbertech;

import com.github.nlatyshev.sbertech.model.Account;
import com.github.nlatyshev.sbertech.model.AccountDetails;
import com.github.nlatyshev.sbertech.model.Order;

import java.util.Map;
import java.util.Optional;

/**
 * @author nlatyshev on 16.06.2017.
 */
public class StockExchange {
    private Map<AccountDetails, Account> accounts;
    private OrderStorage orderStorage;

    public StockExchange(Map<AccountDetails, Account> accounts, OrderStorage orderStorage) {
        this.accounts = accounts;
        this.orderStorage = orderStorage;
    }

    public void execute(Order order) {
        Optional<Order> contraryOrder = orderStorage.pullMatchedTo(order);
        if (contraryOrder.isPresent()) {
            transfer(order.getAmount(),
                    getAccount(order), getAccount(contraryOrder.get()));
            transfer(order.getAmount() * order.getPrice(),
                    getSettlementAccount(contraryOrder.get()), getSettlementAccount(order));
        } else {
            orderStorage.store(order);
        }
    }

    private void transfer(int value, Account from, Account to) {
        from.setAmount(from.getAmount() - value);
        to.setAmount(to.getAmount() + value);
    }

    private Account getAccount(Order order) {
        return accounts.get(order.getAccountDetails());
    }

    private Account getSettlementAccount(Order order) {
        return accounts.get(new AccountDetails(order.getAccountDetails().getClient(), "$"));
    }

}
