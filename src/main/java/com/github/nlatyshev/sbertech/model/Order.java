package com.github.nlatyshev.sbertech.model;

import java.util.Objects;

public class Order {
    private final AccountDetails accountDetails;
    private final int amount;
    private final int price;

    public Order(AccountDetails accountDetails, int amount, int price) {
        this.accountDetails = accountDetails;
        this.amount = amount;
        this.price = price;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(amount, order.amount) &&
                Objects.equals(price, order.price) &&
                Objects.equals(accountDetails, order.accountDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountDetails, amount, price);
    }

    @Override
    public String toString() {
        return "Order{" +
                "accountDetails=" + accountDetails +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
