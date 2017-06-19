package com.github.nlatyshev.sbertech.dao

import com.github.nlatyshev.sbertech.AccountStorage
import com.github.nlatyshev.sbertech.ByteArrayResource
import com.github.nlatyshev.sbertech.TestSupport
import com.github.nlatyshev.sbertech.model.Account
import com.github.nlatyshev.sbertech.model.AccountDetails
import com.github.nlatyshev.sbertech.util.ClientAccountsFormatter
import spock.lang.Specification


class ClientExporterTest extends Specification implements TestSupport{
    def accounts = Mock(AccountStorage)
    def formatter = Mock(ClientAccountsFormatter)
    def exporter = new ClientExporter(accounts, formatter)

    def 'Collect accounts for each client, format and write as new line to resource'() {
        setup:
            def resource = new ByteArrayResource('')
            accounts.findAll() >> [new Account(new AccountDetails('c1', '$'), 1),
                                   new Account(new AccountDetails('c1', 'A'), 2),
                                   new Account(new AccountDetails('c2', '$'), 3)]
            formatter.format('c1', [new Account(new AccountDetails('c1', '$'), 1),
                                    new Account(new AccountDetails('c1', 'A'), 2)]) >> "c1 accounts"
            formatter.format('c2', [new Account(new AccountDetails('c2', '$'), 3)]) >> "c2 accounts"
        when:
            exporter.exportClients(resource)
        then:
            resource.outputStream.toString() == "c1 accounts${lineSep}c2 accounts${lineSep}" as String
    }
}
