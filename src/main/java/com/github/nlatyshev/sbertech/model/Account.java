package com.github.nlatyshev.sbertech.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(amount, account.amount) &&
                Objects.equals(accountDetails, account.accountDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountDetails, amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountDetails=" + accountDetails +
                ", amount=" + amount +
                '}';
    }
}
