package com.github.nlatyshev.sbertech.model;

/**
 * @author nlatyshev on 16.06.2017.
 */
public class Account {
    private final AccountDetails accountDetails;
    private int amount;

    public Account(AccountDetails accountDetails, int amount) {
        this.accountDetails = accountDetails;
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public int getAmount() {
        return amount;
    }
}
