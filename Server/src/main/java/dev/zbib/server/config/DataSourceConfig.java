package dev.zbib.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DataSourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);


    @Value("${spring.datasource.url}")
    private String postgresUrl;

    @Value("${spring.datasource.username}")
    private String postgresUsername;

    @Value("${spring.datasource.password}")
    private String postgresPassword;

    @Value("${h2.datasource.url}")
    private String h2Url;

    @Value("${h2.datasource.username}")
    private String h2Username;

    @Value("${h2.datasource.password}")
    private String h2Password;


    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(postgresUrl);
        dataSource.setUsername(postgresUsername);
        dataSource.setPassword(postgresPassword);


        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(2)) {
                LOGGER.info("Successfully connected to PostgreSQL");
                return dataSource;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to connect to PostgreSQL, falling back to H2 database", e);
        }

        DriverManagerDataSource h2DataSource = new DriverManagerDataSource();
        h2DataSource.setDriverClassName("org.h2.Driver");
        h2DataSource.setUrl(h2Url);
        h2DataSource.setUsername(h2Username);
        h2DataSource.setPassword(h2Password);
        LOGGER.info("Successfully connected to H2 database");

        return h2DataSource;
    }
}
