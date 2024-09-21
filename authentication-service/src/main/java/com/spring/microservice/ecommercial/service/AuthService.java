package com.spring.microservice.ecommercial.service;


import com.spring.microservice.ecommercial.model.UserCredential;
import com.spring.microservice.ecommercial.repository.UserCredentialRepository;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    public String getUserIdByUsername(String username){
        Optional<UserCredential> userCredential = repository.findByUsername(username);
        if (userCredential.isPresent()){
            String userId = String.valueOf(userCredential.get().getId());
            return userId;
        }
        return "";
    }

    public String generateToken(String username) {
        Optional<UserCredential> userCredential = repository.findByUsername(username);
        if (userCredential.isPresent()){
            return jwtService.generateToken(username,(List<GrantedAuthority>) userCredential.get().getAuthorities());
        };
        return null;
    }

    public boolean validateToken(String token) {
        if(!jwtService.validateToken(token)) {
            throw new JwtException("token dogrulanamadi");
        };
        return true;
    }

    public String getUserIdByToken(String authHeader){
        System.out.println("claims  : " + jwtService.extractAllClaims(authHeader));
        System.out.println("claims getSubject() : " + jwtService.extractAllClaims(authHeader).getSubject());
        System.out.println("id : "+jwtService.extractAllClaims(authHeader).get("id",String.class));
        return jwtService.extractAllClaims(authHeader).get("accountId",String.class);
    }




}
