package com.cardano.healthchain.cardano.healthchain.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
public class PostgresDataSourceConfiguration{
    private final Dotenv dotenv;
    public PostgresDataSourceConfiguration(Dotenv dotenv) {
        this.dotenv = dotenv;
    }
    @Bean
    public DataSource createHikariDataSource(){
        HikariConfig config = new HikariConfig();

        String jdbcUrl = dotenv.get("DB_URL");

        config.setJdbcUrl(jdbcUrl);
        config.setUsername(dotenv.get("DB_USERNAME"));
        config.setPassword(dotenv.get("DB_PASSWORD"));

        // Pool settings
        config.setMaximumPoolSize(Integer.parseInt(dotenv.get("DB_POOL_SIZE", "10")));
        config.setMaxLifetime(Long.parseLong(dotenv.get("DB_MAX_LIFETIME_MS", "1800000")));
        config.setConnectionTimeout(Long.parseLong(dotenv.get("DB_CONNECTION_TIMEOUT_MS", "30000")));
        config.setIdleTimeout(Long.parseLong(dotenv.get("DB_IDLE_TIMEOUT_MS", "600000")));

        // Optional
        config.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(config);    }
    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(createHikariDataSource());
    }
}