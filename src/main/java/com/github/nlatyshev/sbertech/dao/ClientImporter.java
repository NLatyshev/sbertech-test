package com.github.nlatyshev.sbertech.dao;

import com.github.nlatyshev.sbertech.AccountStorage;
import com.github.nlatyshev.sbertech.model.Account;
import com.github.nlatyshev.sbertech.util.FlatFileReader;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.function.Function;

public class ClientImporter {
    private Function<String, List<Account>> accountParser;
    private AccountStorage accounts;

    public ClientImporter(Function<String, List<Account>> accountParser, AccountStorage accounts) {
        this.accountParser = accountParser;
        this.accounts = accounts;
    }

    public void importClients(Resource resource) {
        try {
            new FlatFileReader<>(resource, accountParser).forEach(accounts::store);
        } catch (Exception e) {
            throw new RuntimeException("Cannot import clients", e);
        }
    }

}
