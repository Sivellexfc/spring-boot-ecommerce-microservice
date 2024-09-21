package com.spring.microservice.ecommercial.controller;


import com.spring.microservice.ecommercial.dto.RequestUpdateStoreDto;
import com.spring.microservice.ecommercial.dto.ResponseStoreDto;
import com.spring.microservice.ecommercial.model.Store;
import com.spring.microservice.ecommercial.service.StoreService;
import com.spring.microservice.ecommercial.wrapper.ModelWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/store")
public class PostController {

    private final StoreService storeService;

    public PostController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseStoreDto> createStore(@RequestParam String storeName,
                                                        HttpServletRequest request){
        try {
            int userId = storeService.extractUserIDFromHttpRequest(request);
            String token = storeService.getToken(request);
            return ResponseEntity.ok(storeService.createStore(storeName,userId,token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/update/{storeId}")
    public ResponseEntity<ResponseStoreDto> updateStore(@PathVariable String storeId,
                                                        @RequestBody RequestUpdateStoreDto requestUpdateStoreDto){
        try{
            Optional<Store> existingStore = storeService.getStoreByStoreId(storeId);
            if(existingStore.isPresent()){
                Store updatedStore = ModelWrapper.requestUpdateStoreDtoToEntity(requestUpdateStoreDto);
                return ResponseEntity.ok(ModelWrapper.entityToResponseStoreDto(storeService.updateStore(updatedStore)));
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add-product")
    public ResponseEntity addProduct(@RequestParam String productId,
                                     @RequestParam String storeId){
        return storeService.addProduct(productId, storeId);
    }



}

