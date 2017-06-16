package com.github.nlatyshev.sbertech.util;

import com.github.nlatyshev.sbertech.model.Account;
import com.github.nlatyshev.sbertech.model.AccountDetails;

/**
 * @author nlatyshev on 16.06.2017.
 */
public class AccountParser {

    public Account parse(String[] items) {
        return new Account(new AccountDetails(items[0], items[1]), Integer.parseInt(items[0]));
    }

}
