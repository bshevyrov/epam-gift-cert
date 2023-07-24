package com.epam.esm.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Main config class
 */
@Configuration
@ComponentScan("com.epam.esm")
@Import({SpringJDBCConfiguration.class,
        SpringWebConfiguration.class})
public class AppConfig {
    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public MessageSource messageSource () {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(dotenv.get("MESSAGE_SOURCE"));
        return messageSource;
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
