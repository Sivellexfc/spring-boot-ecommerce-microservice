package com.spring.microservice.ecommercial.controller;

import com.spring.microservice.ecommercial.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store")
public class DeleteMapping {

    private final StoreService storeService;

    public DeleteMapping(StoreService storeService) {
        this.storeService = storeService;
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/delete/{storeId}")
    public void deleteStoreByStoreId(@PathVariable String storeId){
        storeService.deleteStoreByStoreId(storeId);
    }


    @org.springframework.web.bind.annotation.DeleteMapping("/deleteAll")
    public ResponseEntity deleteAll(){
        return storeService.deleteAll();
    }

}