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
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ResponseProductDto> editProduct(@RequestParam("file") MultipartFile productImage,
                                                          @RequestParam("productName") String productName,
                                                          @RequestParam("productDescription") String productDescription,
                                                          @RequestParam("categoryName") String categoryName,
                                                          @RequestParam("brand") String brand,
                                                          @RequestParam("price") double price,
                                                          @RequestParam("stock") int stock,
                                                          @RequestParam("productId") long productId,
                                                          HttpServletRequest request
                                                          ){
        System.out.println("product name " + productName);
        RequestProductDto requestProductDto = new RequestProductDto();
        requestProductDto.setProductName(productName);
        requestProductDto.setProductDescription(productDescription);
        requestProductDto.setCategoryName(categoryName);
        requestProductDto.setBrand(brand);
        requestProductDto.setPrice(price);
        requestProductDto.setStock(stock);
        return productService.updateProduct(request,requestProductDto,productImage.getBytes(),productId);
    }

    @PatchMapping("/update/test")
    public String updateTest(HttpServletRequest request){
        return requestService.getUserIdFromAuthenticationService(request);
    }

}

