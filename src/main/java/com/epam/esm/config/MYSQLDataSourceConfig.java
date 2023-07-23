package com.epam.esm.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration for MYSQL DB.
 */
@Profile("local")
@Configuration
public class MYSQLDataSourceConfig {

    private final Dotenv dotenv = Dotenv.load();

    /**
     * @return Configured DataSource for MYSQL.
     */
    @Bean
    public DataSource getDataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName(dotenv.get("LOCAL_DATABASE_DRIVER_CLASS_NAME"));
        ds.setUrl(dotenv.get("LOCAL_DATABASE_URL"));
        ds.setUsername(dotenv.get("LOCAL_DATABASE_USERNAME"));
        ds.setPassword(dotenv.get("LOCAL_DATABASE_PASSWORD"));
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        ds.setMaxIdle(5);
        ds.setMinIdle(2);
        return ds;
    }
}

