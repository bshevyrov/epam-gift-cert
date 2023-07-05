package com.epam.esm.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration for H2  in memory DB.
 * Used when test.
 */
@Profile("DEV")
@Configuration
public class H2DataSourceConfig {
    /**
     * @return Configured DataSource for H2
     */
    @Bean
    public DataSource getDataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:testdb;MODE=MySQL;");
        ds.setUsername("sa");
        ds.setPassword("password");
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        ds.setMaxIdle(5);
        ds.setMinIdle(2);
        return ds;
    }
}
