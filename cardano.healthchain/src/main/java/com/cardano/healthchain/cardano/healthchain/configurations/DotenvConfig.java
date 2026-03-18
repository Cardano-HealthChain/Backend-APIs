package com.cardano.healthchain.cardano.healthchain.configurations;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {
    @Bean
    public Dotenv loadDotenvFile(){
        return Dotenv.load();
    }
}