package com.spring.microservice.ecommercial.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = "Bearer " + getJwtToken();
            requestTemplate.header("Authorization", token);
        };
    }

    private String getJwtToken() {
        return SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
    }


}
