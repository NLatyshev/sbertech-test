package com.github.nlatyshev.sbertech

import com.github.nlatyshev.sbertech.model.Account
import com.github.nlatyshev.sbertech.model.AccountDetails
import spock.lang.Specification


class AccountStorageTest extends Specification {
    def storage = new AccountStorage()

    def 'Store accounts in memory'() {
        when:
            storage.store([new Account(new AccountDetails('c1', '$'), 1),
                           new Account(new AccountDetails('c1', 'A'), 2)])
        then:
            storage.findAll() as Set == [new Account(new AccountDetails('c1', '$'), 1),
                                         new Account(new AccountDetails('c1', 'A'), 2)] as Set
    }

    def 'Find account by details'() {
        setup:
            storage.store([new Account(new AccountDetails('c1', '$'), 1),
                           new Account(new AccountDetails('c1', 'A'), 2)])
        expect:
            storage.findAccount(new AccountDetails('c1', '$')) == new Account(new AccountDetails('c1', '$'), 1)
    }

    def "Return null if account doesn't exist"() {
        setup:
            storage.store([new Account(new AccountDetails('c1', '$'), 1),
                           new Account(new AccountDetails('c1', 'A'), 2)])
        expect:
            storage.findAccount(new AccountDetails('c2', '$')) == null
    }

    def 'Find settlement account for client'() {
        setup:
            storage.store([new Account(new AccountDetails('c1', '$'), 1),
                           new Account(new AccountDetails('c1', 'A'), 2)])
        expect:
            storage.findSettlementAccount('c1') == new Account(new AccountDetails('c1', '$'), 1)
    }

    def "Return null if settlement account doesn't exist"() {
        setup:
            storage.store([new Account(new AccountDetails('c1', 'A'), 2)])
        expect:
            storage.findSettlementAccount('c1') == null
    }
}
