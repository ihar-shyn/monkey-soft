package com.shyn.monkeysoft.demodata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemodataConfig {

    @Bean
    CommandLineRunner commandLineRunner(DemodataService demodataService) {
        return args -> {
            demodataService.createDemoData();
        };
    }
}
