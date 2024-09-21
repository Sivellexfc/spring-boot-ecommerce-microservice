package com.spring.microservice.ecommercial.controller;

import com.spring.microservice.ecommercial.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class GetUserInformationController {

    private final UserService userService;

    public GetUserInformationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getStoreId")
    public ResponseEntity<String> getUserStoreId(@RequestParam int userId) {
        Optional<String> storeIdOptional = userService.getStoreIdByUserId(userId);

        if (storeIdOptional.isPresent()) {
            return ResponseEntity.ok(storeIdOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
