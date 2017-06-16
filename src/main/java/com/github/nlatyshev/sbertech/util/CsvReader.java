package com.github.nlatyshev.sbertech.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author nlatyshev on 16.06.2017.
 */
public class CsvReader<T> {
    private static final Logger log = LoggerFactory.getLogger(CsvReader.class);

    private Resource source;
    private Function<String, T> lineParser;

    public CsvReader(Resource source, Function<String, T> lineParser) {
        this.source = source;
        this.lineParser = lineParser;
    }

    public void forEach(Consumer<T> handler) {
        try (Scanner scanner = new Scanner(source.getInputStream())) {
            while (scanner.hasNextLine()) {
                handler.accept(lineParser.apply(scanner.nextLine()));
            }
        } catch (IOException e) {
            log.error("Cannot load accounts from " + source, e);
            throw new IllegalArgumentException("Cannot load accounts from " + source, e);
        }
    }
}
