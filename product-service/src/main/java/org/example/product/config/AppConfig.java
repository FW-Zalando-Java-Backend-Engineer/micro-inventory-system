package org.example.product.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for shared application-level beans.
 */
@Configuration
public class AppConfig {

    /**
     * Bean definition for RestTemplate to allow HTTP communication
     * with external services like inventory-service.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
