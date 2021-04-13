package com.parks.parks.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class BeanConfiguration {

    @Bean
    public WebClient getWebClientBuilder(){
        return WebClient.builder()
                .baseUrl("https://developer.nps.gov/api/v1")
                .build();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
