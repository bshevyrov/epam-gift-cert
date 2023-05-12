package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.epam.esm")
@Import({SpringJDBCConfiguration.class,
        SpringWebConfiguration.class})
public class MainConfiguration {
}
