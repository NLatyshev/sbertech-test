package com.github.nlatyshev.sbertech.util

import com.github.nlatyshev.sbertech.model.AccountDetails
import com.github.nlatyshev.sbertech.model.Order
import spock.lang.Specification


class OrderParserTest extends Specification {
    def parser = new OrderParser()

    def 'Parse string array to order'() {
        expect:
            parser.parse(['c1', 'b', 'a', '1', '2'] as String[]) == new Order(new AccountDetails('c1', 'a'), 1, 2)
            parser.parse(['c1', 's', 'a', '1', '2'] as String[]) == new Order(new AccountDetails('c1', 'a'), -1, 2)
    }

    def 'Throw exception if array length is not 6 or order type is not b or s'() {
        when:
            parser.parse(input as String[])
        then:
            thrown(IllegalArgumentException)
        where:
            input << [['c1', 'b', 'a', '1'],
                      ['c1', 'b', 'a', '1', '2', '3'],
                      ['c1', 'x', 'a', '1', '2']]
    }

    def 'Throw exception if cannot parse amount or price to int'() {
        when:
            parser.parse(input as String[])
        then:
            thrown(NumberFormatException)
        where:
            input << [['c1', 'b', 'a', 'x', '2'],
                      ['c1', 'b', 'a', '1', 'x']]
    }
}
