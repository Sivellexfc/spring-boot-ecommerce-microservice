package com.spring.microservice.ecommercial.service;

import com.spring.microservice.ecommercial.model.UserCredential;
import com.spring.microservice.ecommercial.repository.UserCredentialRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserCredentialRepository userRepository;
    private final AuthService authService;

    public UserService(UserCredentialRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public Optional<String> getStoreIdByUserId(int userId) {
        return userRepository.findStoreIdById(userId).get().getStoreId().describeConstable();
    }

    public Optional<Long> getStoreIdByUsername(String username) {
        return userRepository.findStoreIdByUsername(username);
    }

    public UserCredential setStoreIdByUserId(int userId, String storeId) {
        Optional<UserCredential> response = userRepository.findById(userId);

        if (response.isPresent()) {
            UserCredential userCredential = response.get();
            userCredential.setStoreId(storeId);
            System.out.println("storeId güncellemesi : " + userCredential.getStoreId());
            return userRepository.save(userCredential);
        }

        throw new RuntimeException("User with ID " + userId + " not found");
    }

}
