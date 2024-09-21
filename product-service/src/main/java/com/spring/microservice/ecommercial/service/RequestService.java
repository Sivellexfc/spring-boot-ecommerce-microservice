package com.spring.microservice.ecommercial.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RequestService {

    private final RestTemplate template;

    public RequestService(RestTemplate template) {
        this.template = template;
    }

    public String getUserIdFromAuthenticationService(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            ResponseEntity<String> responseEntity = template.exchange("http://localhost:8889/auth/getUserIdByAuthHeader?authHeader=" + token, HttpMethod.GET,entity,String.class);
            return responseEntity.getBody();
        }
        return "error";

    }
}
