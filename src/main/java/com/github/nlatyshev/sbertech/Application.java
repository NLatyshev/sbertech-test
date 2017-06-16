package com.github.nlatyshev.sbertech;

import com.github.nlatyshev.sbertech.model.Account;
import com.github.nlatyshev.sbertech.model.AccountDetails;
import com.github.nlatyshev.sbertech.model.Order;
import com.github.nlatyshev.sbertech.util.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.PathResource;

import javax.annotation.Resource;
import java.util.Map;
import java.util.function.Function;

/**
 * @author nlatyshev on 16.06.2017.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Resource
    private Map<AccountDetails, Account> accounts;

    @Autowired
    private StockExchange stockExchange;

    @Autowired
    @Qualifier("orderParser")
    private Function<String, Order> orderParser;

    @Autowired
    @Qualifier("accountParser")
    private Function<String, Account> accountParser;

    @Autowired
    @Qualifier("accountFormatter")
    private Function<Account, String> accountFormatter;

    @Override
    public void run(String... args) throws Exception {
        importClients(args[0]);

        executeOrders(args[1]);

        exportAccounts(args[2]);
    }

    private void exportAccounts(String arg) {
        accounts.values().stream().forEach(account ->
                System.out.println(accountFormatter.apply(account))
        );
    }

    private void executeOrders(String arg) {
        new CsvReader<>(new PathResource(arg), orderParser).forEach(stockExchange::execute);
    }

    private void importClients(String arg) {
        new CsvReader<>(new PathResource(arg), accountParser).forEach((account ->
                accounts.put(account.getAccountDetails(), account)));
    }
}
