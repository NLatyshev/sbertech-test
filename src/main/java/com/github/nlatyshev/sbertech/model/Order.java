package com.github.nlatyshev.sbertech.model;


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
}
