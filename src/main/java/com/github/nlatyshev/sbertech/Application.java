package com.github.nlatyshev.sbertech;

import com.github.nlatyshev.sbertech.config.StockExchangeSpringConfig;
import com.github.nlatyshev.sbertech.dao.ClientExporter;
import com.github.nlatyshev.sbertech.dao.ClientImporter;
import com.github.nlatyshev.sbertech.dao.OrderImporter;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.PathResource;

@SpringBootApplication
@Import({StockExchangeSpringConfig.class})
public class Application implements CommandLineRunner {

    @Autowired
    private StockExchange stockExchange;

    @Autowired
    private ClientImporter clientImporter;

    @Autowired
    private ClientExporter clientExporter;

    @Autowired
    private OrderImporter orderImporter;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Options options = new Options()
                .addOption(OptionBuilder
                        .hasArg()
                        .isRequired()
                        .withDescription("path to input clients file")
                        .create("clientsIn"))
                .addOption(OptionBuilder
                        .hasArg()
                        .isRequired()
                        .withDescription("path to orders file")
                        .create("orders"))
                .addOption(OptionBuilder
                        .hasArg()
                        .isRequired()
                        .withDescription("path to file to write clients current state")
                        .create("clientsOut"));
        try {
            CommandLine cl = new BasicParser().parse(options, args);

            clientImporter.importClients(new PathResource(cl.getOptionValue("clientsIn")));

            orderImporter.importOrders(new PathResource(cl.getOptionValue("orders")), stockExchange::execute);

            clientExporter.exportClients(new PathResource(cl.getOptionValue("clientsOut")));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            new HelpFormatter().printHelp("Stock Exchange", options, true);
        }
    }
}
