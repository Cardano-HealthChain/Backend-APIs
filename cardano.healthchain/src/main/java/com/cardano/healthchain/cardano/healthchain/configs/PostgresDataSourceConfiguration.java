package com.cardano.healthchain.cardano.healthchain.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
public class PostgresDataSourceConfiguration{
//    @Bean
//    public DataSource createHikariDataSource(){
//        HikariConfig hikariConfig = new HikariConfig();
//        SET HIKARI CONFIGURATION INFORMATION LATER
//        return new HikariDataSource(hikariConfig);
//    }
//    @Bean
//    public JdbcTemplate jdbcTemplate(){
//        return new JdbcTemplate(createHikariDataSource());
//    }
}