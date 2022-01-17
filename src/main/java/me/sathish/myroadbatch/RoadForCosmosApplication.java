package me.sathish.myroadbatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication

public class RoadForCosmosApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoadForCosmosApplication.class);


    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RoadForCosmosApplication.class);
        Properties properties = new Properties();
        properties.put("spring.batch.job.enabled", false);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
