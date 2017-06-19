package com.github.nlatyshev.sbertech.dao

import com.github.nlatyshev.sbertech.AccountStorage
import com.github.nlatyshev.sbertech.ByteArrayResource
import com.github.nlatyshev.sbertech.TestSupport
import com.github.nlatyshev.sbertech.model.Account
import com.github.nlatyshev.sbertech.model.AccountDetails
import spock.lang.Specification

import java.util.function.Function

class ClientImporterTest extends Specification implements TestSupport {
    def Function<String, List<Account>> accountParser = Mock(Function)
    def accounts = Mock(AccountStorage)
    def importer = new ClientImporter(accountParser, accounts)

    def 'Read, parse and store client accounts'() {
        setup:
            def resource = new ByteArrayResource("c1${lineSep}c2")
            accountParser.apply('c1') >> [new Account(new AccountDetails('c1', '$'), 1),
                                          new Account(new AccountDetails('c1', 'A'), 2)]
            accountParser.apply('c2') >> [new Account(new AccountDetails('c2', '$'), 3)]
        when:
            importer.importClients(resource)
        then:
            1 * accounts.store([new Account(new AccountDetails('c1', '$'), 1),
                                new Account(new AccountDetails('c1', 'A'), 2)])
            1 * accounts.store([new Account(new AccountDetails('c2', '$'), 3)])
    }
}
