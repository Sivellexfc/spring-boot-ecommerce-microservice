package com.spring.microservice.ecommercial.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtUtil {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private final RestTemplate template;

    public JwtUtil(RestTemplate template) {
        this.template = template;
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private List<String> getRoles(final String authHeader) {
        List<String> rolesList = new ArrayList<>();
        ResponseEntity<String[]> response = template.
                getForEntity("http://localhost:9898/auth/get-roles?authHeader=" + authHeader, String[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String[] rolesArray = response.getBody();
            assert rolesArray != null;
            rolesList = Arrays.asList(rolesArray);
        }
        return rolesList;
    }
}
