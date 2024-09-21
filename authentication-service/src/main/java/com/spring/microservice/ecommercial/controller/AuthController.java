package com.spring.microservice.ecommercial.controller;


import com.spring.microservice.ecommercial.dto.AuthRequest;
import com.spring.microservice.ecommercial.model.UserCredential;
import com.spring.microservice.ecommercial.model.UserRole;
import com.spring.microservice.ecommercial.service.AuthService;
import com.spring.microservice.ecommercial.service.JwtService;
import com.spring.microservice.ecommercial.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;
    private final UserService userService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


//    @PostMapping("/register")
//    public String addNewUser(@RequestBody UserCredential user) {
//        return service.saveUser(user);
//    }

    @PostMapping("/register-customer")
    public String registerCustomerUser(@RequestBody UserCredential userCredential) {

        List<GrantedAuthority> authorities = UserRole.CUSTOMER.getAuthorities();
        userCredential.setAuthorities(UserRole.CUSTOMER);

        return service.saveUser(userCredential);
    }

    @PostMapping("/register-seller")
    public String registerSellerUser(@RequestBody UserCredential userCredential) {

        List<GrantedAuthority> authorities = UserRole.SELLER.getAuthorities();
        userCredential.setAuthorities(UserRole.SELLER);
        return service.saveUser(userCredential);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return service.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestParam("token") String token) {
        return service.validateToken(token);
    }

    @GetMapping("/getUserIdByAuthHeader")
    public String getAccountId(@RequestParam("token") String token){
        return service.getUserIdByToken(token);
    }

    @GetMapping("/testing")
    public String test(@RequestParam("token") String token){
        return "test başarılı";
    }

    @PostMapping("/setStoreID")
    public ResponseEntity setStoreID(@RequestParam String storeID,
                                     HttpServletRequest request
                                     //@RequestHeader("Authorization") String token
    ) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String userID = jwtService.extractUserId(token);
        return ResponseEntity.ok(userService.setStoreIdByUserId(Integer.parseInt(userID),storeID));
    }

    @GetMapping("get-roles")
    public List getRoles(@RequestParam("authHeader") String authHeader){
        return jwtService.getClaims(authHeader);
    }


}
