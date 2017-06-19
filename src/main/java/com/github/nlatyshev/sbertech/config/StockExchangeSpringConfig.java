package com.github.nlatyshev.sbertech.config;

import com.github.nlatyshev.sbertech.AccountStorage;
import com.github.nlatyshev.sbertech.OrderStorage;
import com.github.nlatyshev.sbertech.StockExchange;
import com.github.nlatyshev.sbertech.dao.ClientExporter;
import com.github.nlatyshev.sbertech.dao.ClientImporter;
import com.github.nlatyshev.sbertech.dao.OrderImporter;
import com.github.nlatyshev.sbertech.model.Account;
import com.github.nlatyshev.sbertech.model.Order;
import com.github.nlatyshev.sbertech.util.ClientAccountsFormatter;
import com.github.nlatyshev.sbertech.util.ClientAccountsParser;
import com.github.nlatyshev.sbertech.util.OrderParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;

@Configuration
public class StockExchangeSpringConfig {

    @Bean
    public OrderStorage orderStorage() {
        return new OrderStorage();
    }

    @Bean
    public AccountStorage accountStorage() {
        return new AccountStorage();
    }

    @Bean
    public StockExchange stockExchange(OrderStorage orderStorage, AccountStorage accountStorage) {
        return new StockExchange(accountStorage, orderStorage);
    }

    @Bean
    public ClientAccountsFormatter clientAccountsFormatter() {
        return new ClientAccountsFormatter();
    }

    @Bean
    public Function<String, String[]> splitter() {
        return line -> line.split("\t");
    }

    @Bean
    public Function<String, Order> orderParser(@Qualifier("splitter") Function<String, String[]> splitter) {
        return splitter.andThen(new OrderParser()::parse);
    }

    @Bean
    public Function<String, List<Account>> accountParser(@Qualifier("splitter") Function<String, String[]> splitter) {
        return splitter.andThen(new ClientAccountsParser()::parse);
    }

    @Bean
    public ClientImporter clientsImporter(@Qualifier("accountParser") Function<String, List<Account>> accountParser, AccountStorage accounts) {
        return new ClientImporter(accountParser, accounts);
    }

    @Bean
    public ClientExporter clientsExporter(AccountStorage accounts, ClientAccountsFormatter clientAccountsFormatter) {
        return new ClientExporter(accounts, clientAccountsFormatter);
    }

    @Bean
    public OrderImporter orderImporter(@Qualifier("orderParser") Function<String, Order> orderParser) {
        return new OrderImporter(orderParser);
    }

}
