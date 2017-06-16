package com.github.nlatyshev.sbertech.config;

import com.github.nlatyshev.sbertech.OrderStorage;
import com.github.nlatyshev.sbertech.StockExchange;
import com.github.nlatyshev.sbertech.model.Account;
import com.github.nlatyshev.sbertech.model.AccountDetails;
import com.github.nlatyshev.sbertech.model.Order;
import com.github.nlatyshev.sbertech.util.AccountParser;
import com.github.nlatyshev.sbertech.util.OrderParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author nlatyshev on 16.06.2017.
 */
@Configuration
public class StockExchangeSpringConfig {

    @Resource
    private Map<AccountDetails, Account> accounts;

    @Bean
    public OrderStorage orderStorage() {
        return new OrderStorage();
    }

    @Bean
    public Map<AccountDetails, Account> accounts() {
        return new LinkedHashMap<>();
    }

    @Bean
    public StockExchange stockExchange(OrderStorage orderStorage) {
        return new StockExchange(accounts, orderStorage);
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
    public Function<String, Account> accountParser(@Qualifier("splitter") Function<String, String[]> splitter) {
        return splitter.andThen(new AccountParser()::parse);
    }

}
