package com.spring.microservice.ecommercial.controller;


import com.spring.microservice.ecommercial.client.AccountServiceClient;
import com.spring.microservice.ecommercial.client.AuthenticationServiceClient;
import com.spring.microservice.ecommercial.dto.RequestProductDto;
import com.spring.microservice.ecommercial.dto.ResponseProductDto;
import com.spring.microservice.ecommercial.service.ProductService;
import com.spring.microservice.ecommercial.service.RequestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/product")
public class PatchController {

    private final ProductService productService;
    private final RequestService requestService;
    private final AccountServiceClient accountServiceClient;
    private final RestTemplate template;
    private final AuthenticationServiceClient authenticationServiceClient;

    public PatchController(ProductService productService, RequestService requestService, AccountServiceClient accountServiceClient, RestTemplate template, AuthenticationServiceClient authenticationServiceClient) {
        this.productService = productService;
        this.requestService = requestService;
        this.accountServiceClient = accountServiceClient;
        this.template = template;
        this.authenticationServiceClient = authenticationServiceClient;
    }

    @SneakyThrows
    @PatchMapping("/update")
    public ResponseEntity<ResponseProductDto> editProduct(@RequestParam String productId,
                                                          @RequestBody RequestProductDto requestProductDto,
                                                          @RequestParam String accountId){
        return productService.updateProduct(requestProductDto,accountId,productId);
    }

    @PatchMapping("/update/test")
    public String updateTest(HttpServletRequest request){
        return requestService.getUserIdFromAuthenticationService(request);
    }

}

