package com.github.nlatyshev.sbertech

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@ContextConfiguration(classes = [Application])
class ApplicationTest extends Specification implements TestSupport{
    Path tmpDir = Paths.get(System.getProperty('java.io.tmpdir') + File.separator + 'stock' + File.separator)
    Resource clientsIn = new ClassPathResource('clients.txt')
    Resource orders = new ClassPathResource('orders.txt')
    Path clientsOut = tmpDir.resolve('clientsOut')

    @Autowired Application app

    def setup() {
        cleanTmpFolder()
        Files.createDirectory(tmpDir);
    }

    def 'Import clients, process orders, export clients'() {
        when:
            app.main(['-clientsIn',clientsIn.getFile().getAbsolutePath(),
                      '-orders', orders.getFile().getAbsolutePath(),
                      '-clientsOut', clientsOut.toAbsolutePath().toString()] as String[])
        then:
            clientsOut.readLines() == ['C1\t940\t145\t240\t760\t320',
                                       'C2\t4290\t385\t120\t950\t560',
                                       'C3\t2880\t-30\t0\t0\t0']
    }

    def cleanup() {
        cleanTmpFolder()
    }

    def cleanTmpFolder() {
        if (Files.exists(tmpDir)) {
            Files.newDirectoryStream(tmpDir).forEach { p -> Files.delete(p) }
            Files.delete(tmpDir)
        }
    }
}
