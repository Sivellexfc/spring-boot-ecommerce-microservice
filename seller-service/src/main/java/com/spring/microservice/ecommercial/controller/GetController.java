package com.spring.microservice.ecommercial.controller;


import com.spring.microservice.ecommercial.client.AccountServiceClient;
import com.spring.microservice.ecommercial.dto.ResponseStoreDto;
import com.spring.microservice.ecommercial.model.Store;
import com.spring.microservice.ecommercial.service.StoreService;
import com.spring.microservice.ecommercial.wrapper.ModelWrapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/store")
public class GetController {

    private final StoreService storeService;
    private final AccountServiceClient accountServiceClient;

    public GetController(StoreService storeService, AccountServiceClient accountServiceClient) {
        this.storeService = storeService;
        this.accountServiceClient = accountServiceClient;
    }

    @GetMapping("/id/{storeId}")
    public ResponseEntity<ResponseStoreDto> getStoreByStoreId(@PathVariable @NotNull String storeId){
        Optional<Store> store = storeService.getStoreByStoreId(storeId);
        if(store.isPresent()){
            return ResponseEntity.ok(ModelWrapper.entityToResponseStoreDto(store.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseStoreDto>> getAllStores(){
        List<ResponseStoreDto> responseStoreDtoList =
                storeService.getAllStores()
                        .stream()
                        .map(ModelWrapper::entityToResponseStoreDto)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(responseStoreDtoList);
    }

    @GetMapping("/storeName/{storeName}")
    public ResponseEntity<ResponseStoreDto> getStoreByStoreName(@PathVariable String storeName){
        ResponseStoreDto response = ModelWrapper.entityToResponseStoreDto(storeService.getStoreByStoreName(storeName));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/storeName")
    public ResponseEntity<List<ResponseStoreDto>> getStoresByStoreNameStartingWith(@RequestParam String prefix){
        List<ResponseStoreDto> response = storeService.getStoresByStoreNameStartingWith(prefix)
                .stream()
                .map(ModelWrapper::entityToResponseStoreDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<ResponseStoreDto>> getStoresByPointRange(@RequestParam double firstValue,
                                                                        double secondValue){
        List<ResponseStoreDto> responseStoreDtoList = storeService.getStoresByPointRange(firstValue, secondValue)
                .stream()
                .map(ModelWrapper::entityToResponseStoreDto).toList();
        return ResponseEntity.ok(responseStoreDtoList);

    }

    @GetMapping("/owner/{ownerAccountId}")
    public ResponseEntity<ResponseStoreDto> getStoreByOwnerAccountId(@PathVariable long ownerAccountId){
        return ResponseEntity.ok(ModelWrapper.entityToResponseStoreDto(storeService.getStoreByOwnerAccountId(ownerAccountId)));
    }


    @GetMapping("/test")
    public String test(){
        return "test";
    }

}


