package com.github.nlatyshev.sbertech.util

import com.github.nlatyshev.sbertech.model.Account
import com.github.nlatyshev.sbertech.model.AccountDetails
import spock.lang.Specification


class ClientAccountsFormatterTest extends Specification {
    def formatter = new ClientAccountsFormatter()

    def "Format client accounts to single line"() {
        expect:
            formatter.format('c1', [new Account(new AccountDetails('c1', '$'), 1),
                                    new Account(new AccountDetails('c1', 'A'), 2),
                                    new Account(new AccountDetails('c1', 'B'), 3),
                                    new Account(new AccountDetails('c1', 'C'), 4),
                                    new Account(new AccountDetails('c1', 'D'), 5)]) == 'c1\t1\t2\t3\t4\t5'
    }
}
