package com.github.nlatyshev.sbertech.dao;

import com.github.nlatyshev.sbertech.AccountStorage;
import com.github.nlatyshev.sbertech.model.Account;
import com.github.nlatyshev.sbertech.util.ClientAccountsFormatter;
import org.springframework.core.io.WritableResource;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ClientExporter {
    private AccountStorage accounts;
    private ClientAccountsFormatter clientAccountsFormatter;

    public ClientExporter(AccountStorage accounts, ClientAccountsFormatter clientAccountsFormatter) {
        this.accounts = accounts;
        this.clientAccountsFormatter = clientAccountsFormatter;
    }

    public void exportClients(WritableResource resource) {
        try (Writer out = getWriter(resource)) {
            accounts.findAll().stream()
                    .collect(Collectors.groupingBy(account -> account.getAccountDetails().getClient(), LinkedHashMap::new, Collectors.toList()))
                    .forEach((clientName, accounts) ->
                            writeLine(out, clientName, accounts, System.getProperty("line.separator")));
        } catch (IOException e) {
            throw new RuntimeException("Cannot export clients accounts", e);
        }
    }

    private void writeLine(Writer out, String clientName, List<Account> accounts, String lineSeparator) {
        try {
            out.write(clientAccountsFormatter.format(clientName, accounts) + lineSeparator);
        } catch (IOException e) {
            throw new RuntimeException("Cannot export clients accounts", e);
        }
    }

    private Writer getWriter(WritableResource resource) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(resource.getOutputStream()));
    }
}
