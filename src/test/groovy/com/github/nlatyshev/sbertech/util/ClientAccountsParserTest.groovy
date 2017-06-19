package com.github.nlatyshev.sbertech.util

import com.github.nlatyshev.sbertech.model.Account
import com.github.nlatyshev.sbertech.model.AccountDetails
import spock.lang.Specification


class ClientAccountsParserTest extends Specification {
    def parser = new ClientAccountsParser()

    def "Parse string array to client accounts"() {
        expect:
            parser.parse(['c1', '1', '2', '3', '4', '5'] as String[]) == [new Account(new AccountDetails('c1', '$'), 1),
                                                                          new Account(new AccountDetails('c1', 'A'), 2),
                                                                          new Account(new AccountDetails('c1', 'B'), 3),
                                                                          new Account(new AccountDetails('c1', 'C'), 4),
                                                                          new Account(new AccountDetails('c1', 'D'), 5)]
    }

    def 'Throw exception if array length is not 6'() {
        when:
            parser.parse(input as String[])
        then:
            thrown(IllegalArgumentException)
        where:
            input << [['c1', '1', '2', '3', '4'], ['c1', '1', '2', '3', '4', '5', '6']]
    }

    def 'Throw exception if cannot parse amount to int'() {
        when:
            parser.parse(input as String[])
        then:
            thrown(NumberFormatException)
        where:
            input << [['c1', 'x', '2', '3', '4', '5'],
                      ['c1', '1', 'x', '3', '4', '5'],
                      ['c1', '1', '2', 'x', '4', '5'],
                      ['c1', '1', '2', '3', 'x', '5'],
                      ['c1', '1', '2', '3', '4', 'x']]
    }
}
