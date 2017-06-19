package com.github.nlatyshev.sbertech;

import com.github.nlatyshev.sbertech.model.Account;
import com.github.nlatyshev.sbertech.model.AccountDetails;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AccountStorage {
    public static final String SETTLEMENT_ACCOUNT_NAME = "$";
    private Map<AccountDetails, Account> accounts = new LinkedHashMap<>();

    public void store(List<Account> accounts) {
        accounts.forEach(account -> this.accounts.put(account.getAccountDetails(), account));
    }

    public Account findAccount(AccountDetails accountDetails) {
        return accounts.get(accountDetails);
    }

    public Account findSettlementAccount(String clientName) {
        return accounts.get(new AccountDetails(clientName, SETTLEMENT_ACCOUNT_NAME));
    }

    public Collection<Account> findAll() {
        return accounts.values();
    }

}
