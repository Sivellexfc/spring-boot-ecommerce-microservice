package com.spring.microservice.ecommercial.repository;

import com.spring.microservice.ecommercial.model.UserCredential;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository  extends JpaRepository<UserCredential,Integer> {
    Optional<UserCredential> findByUsername(String username);

    Optional<UserCredential> findById(int id);

    Optional<Long> findStoreIdByUsername(String username);

    Optional<UserCredential> findStoreIdById(long id);


}