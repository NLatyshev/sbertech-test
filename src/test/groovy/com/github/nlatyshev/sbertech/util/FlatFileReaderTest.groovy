package com.github.nlatyshev.sbertech.util

import com.github.nlatyshev.sbertech.ByteArrayResource
import com.github.nlatyshev.sbertech.TestSupport
import org.springframework.core.io.Resource
import spock.lang.Specification

import java.util.function.Consumer
import java.util.function.Function

class FlatFileReaderTest extends Specification implements TestSupport {
    def resource = new ByteArrayResource("l1${lineSep}l2")
    Consumer<String> handler = Mock(Consumer)
    FlatFileReader<String> reader = new FlatFileReader(resource, Function.identity())

    def 'Read file line by line, parse and pass to handler'() {
        when:
            reader.forEach(handler)
        then:
            1 * handler.accept('l1')
            1 * handler.accept('l2')
    }
}
