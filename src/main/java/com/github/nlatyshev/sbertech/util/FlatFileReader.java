package com.github.nlatyshev.sbertech.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class FlatFileReader<T> {
    private static final Logger log = LoggerFactory.getLogger(FlatFileReader.class);

    private Resource source;
    private Function<String, T> lineParser;

    public FlatFileReader(Resource source, Function<String, T> lineParser) {
        this.source = source;
        this.lineParser = lineParser;
    }

    public void forEach(Consumer<T> handler) throws IOException {
        long lineNum = 0;
        try (Scanner scanner = new Scanner(source.getInputStream())) {
            while (scanner.hasNextLine()) {
                handler.accept(lineParser.apply(scanner.nextLine()));
                lineNum++;
            }
        } catch (IOException e) {
            log.error("Cannot load from " + source, e);
            throw new IOException("Cannot load from " + source, e);
        } catch (Exception e) {
            log.error(String.format("Got error on line %s: %s", lineNum, e.getMessage()), e);
            throw new IllegalArgumentException(String.format("Got error on line %s: %s", lineNum, e.getMessage()), e);
        }
    }
}
