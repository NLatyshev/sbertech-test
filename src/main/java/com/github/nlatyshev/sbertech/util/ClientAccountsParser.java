package com.github.nlatyshev.sbertech.util;

import com.github.nlatyshev.sbertech.AccountStorage;
import com.github.nlatyshev.sbertech.model.Account;
import com.github.nlatyshev.sbertech.model.AccountDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientAccountsParser {

    public List<Account> parse(String[] items) {
        if (items.length == 6) {
            String clientName = items[0];
            List<Account> clientAccounts = new ArrayList<>(5);
            clientAccounts.add(new Account(new AccountDetails(clientName, AccountStorage.SETTLEMENT_ACCOUNT_NAME), Integer.parseInt(items[1])));
            clientAccounts.add(new Account(new AccountDetails(clientName, "A"), Integer.parseInt(items[2])));
            clientAccounts.add(new Account(new AccountDetails(clientName, "B"), Integer.parseInt(items[3])));
            clientAccounts.add(new Account(new AccountDetails(clientName, "C"), Integer.parseInt(items[4])));
            clientAccounts.add(new Account(new AccountDetails(clientName, "D"), Integer.parseInt(items[5])));
            return clientAccounts;
        }
        throw new IllegalArgumentException(String.format("Cannot create client accounts from %s: expected 6 items, but got %s",
                Arrays.toString(items), items.length));
    }
}
