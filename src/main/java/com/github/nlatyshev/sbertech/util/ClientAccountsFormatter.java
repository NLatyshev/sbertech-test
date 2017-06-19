package com.github.nlatyshev.sbertech.util;

import com.github.nlatyshev.sbertech.model.Account;

import java.util.List;
import java.util.stream.Collectors;

public class ClientAccountsFormatter {

    public String format(String clientName, List<Account> clientAccounts) {
        return String.format("%s\t%s",
                clientName,
                clientAccounts.stream().map(Account::getAmount).map(Object::toString).collect(Collectors.joining("\t")));
    }
}
