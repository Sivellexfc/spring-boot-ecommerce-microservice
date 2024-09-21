package com.spring.microservice.ecommercial.client;

import com.spring.microservice.ecommercial.config.FeignConfig;
import com.spring.microservice.ecommercial.dto.ResponseAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="gateway-service")
public interface AccountServiceClient {

    @GetMapping("/getUserIdByAuthHeader")
    String getAccountIdByAuthHeader(@RequestParam("token") String token);

    @PostMapping("/auth/setStoreID")
    ResponseEntity setStoreID(@RequestParam String storeID, @RequestHeader("Authorization") String token,
                              @RequestHeader("user-agent") String userAgent);

//    @GetMapping("/{accountId}")
//    ResponseEntity<ResponseAccountDto> getAccount(@PathVariable String accountId);
//
//    @GetMapping("/getAccountsByStoreNamePrefix")
//    ResponseEntity<List<ResponseAccountDto>> getAccountsByStoreNamePrefix(@RequestParam(name = "storeNamePrefix") String storeNamePrefix);
//
//    @GetMapping("/store")
//    String getStoreIdByAccountId(@RequestParam(name = "accountId") String accountId);
//
//    @GetMapping("/deneme")
//    String denemeAc();
}
