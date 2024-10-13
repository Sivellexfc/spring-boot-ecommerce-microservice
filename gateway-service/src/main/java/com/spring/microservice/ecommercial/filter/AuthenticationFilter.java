package com.spring.microservice.ecommercial.filter;


import com.spring.microservice.ecommercial.client.AuthenticationServiceClient;
import com.spring.microservice.ecommercial.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator validator;
    private AuthenticationServiceClient authenticationClient;
    private final RestTemplate template;

    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil, RestTemplate template) {
        super(Config.class);
        this.validator = validator;
        this.template = template;
        //this.jwtUtil = jwtUtil;
    }
    @Autowired
    public void setAuthenticationClient(AuthenticationServiceClient authenticationServiceClient){
        this.authenticationClient = authenticationServiceClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                System.out.println(exchange.getAttributes() + "\n");
                exchange.getAttributes().put("accountId","2222");
                System.out.println(exchange.getAttributes());
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                    ResponseEntity<String[]> response = template.
                            getForEntity("http://localhost:9898/auth/get-roles?authHeader=" + authHeader, String[].class);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        String[] rolesArray = response.getBody();
                        assert rolesArray != null;
                        List<String> rolesList = Arrays.asList(rolesArray);

                        if (rolesList.contains("SELLER")) {
                            if (!exchange.getRequest().getURI().getPath().contains("store") &&
                                    !exchange.getRequest().getURI().getPath().contains("customer") &&
                                    !exchange.getRequest().getURI().getPath().contains("order") &&
                                    !exchange.getRequest().getURI().getPath().contains("product")) {

                                throw new RuntimeException("unauthorized access as SELLER");
                            }
                        }
                        if (rolesList.contains("CUSTOMER")) {
                            if (!exchange.getRequest().getURI().getPath().contains("product/add/comment") &&
                                    !exchange.getRequest().getURI().getPath().contains("order")) {
                                throw new RuntimeException("unauthorized access as CUSTOMER");
                            }
                        }

                    }
                    else {
                        System.out.println("hatalı bir dönüş");
                    }
                }
                try {
                    String responseValidate =  template.getForObject("http://localhost:9898/auth/validate?token=" + authHeader, String.class);
                    System.out.println("responseValidate :" + responseValidate);
                } catch (Exception e) {
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}