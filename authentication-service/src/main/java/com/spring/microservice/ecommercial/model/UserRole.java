package com.spring.microservice.ecommercial.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum UserRole {
    CUSTOMER,
    SELLER,
    ADMIN;

    public List<GrantedAuthority> getAuthorities() {
        return Stream.of("ROLE_" + this.name())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
