package com.epam.esm.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration for H2  in memory DB.
 * Used when test.
 */
@Profile("test")
@Configuration
public class H2DataSourceConfig {
    /**
     * @return Configured DataSource for H2
     */
    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public DataSource getDataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName(dotenv.get("TEST_DATABASE_DRIVER_CLASS_NAME"));
        ds.setUrl(dotenv.get("TEST_DATABASE_URL"));
        ds.setUsername(dotenv.get("TEST_DATABASE_USERNAME"));
        ds.setPassword(dotenv.get("TEST_DATABASE_PASSWORD"));
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        ds.setMaxIdle(5);
        ds.setMinIdle(2);
        return ds;
    }
}
