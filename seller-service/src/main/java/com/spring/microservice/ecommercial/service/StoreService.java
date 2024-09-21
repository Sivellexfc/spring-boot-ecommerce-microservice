package com.spring.microservice.ecommercial.service;

import com.spring.microservice.ecommercial.client.AccountServiceClient;
import com.spring.microservice.ecommercial.dto.ResponseStoreDto;
import com.spring.microservice.ecommercial.model.Store;
import com.spring.microservice.ecommercial.repository.StoreRepository;
import com.spring.microservice.ecommercial.wrapper.ModelWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.NotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final JwtService jwtService;
    private final AccountServiceClient accountServiceClient;

    public StoreService(StoreRepository storeRepository, JwtService jwtService, AccountServiceClient accountServiceClient) {
        this.storeRepository = storeRepository;
        this.jwtService = jwtService;
        this.accountServiceClient = accountServiceClient;
    }

    public ResponseEntity deleteAll() {
        storeRepository.deleteAll();
        return ResponseEntity.ok().body(null);
    }

    public ResponseStoreDto createStore(String storeName, int userID, String token) {

        Store store = new Store();
        store.setStoreName(storeName);
        store.setOwnerAccountId(userID);

        Store savedStore = storeRepository.save(store);

        ResponseStoreDto responseStoreDto = ModelWrapper.entityToResponseStoreDto(savedStore);

        try {
            String storeId = savedStore.getId();
            System.out.println("TOKEN : "+token);
            accountServiceClient.setStoreID(storeId, "Bearer "+ token, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            return responseStoreDto;

        } catch (Exception e) {
            throw new RuntimeException("Failed to update user account with store ID", e);
        }
    }


    public void deleteStoreByStoreId(String storeId){
        storeRepository.deleteById(storeId);
    }

    public Store updateStore(Store store){
        return storeRepository.save(store);
    }

    public Optional<Store> getStoreByStoreId(String storeId){
        return storeRepository.findById(storeId);
    }

    public List<Store> getAllStores(){
        return storeRepository.findAll();
    }

    public Store getStoreByStoreName(String storeName){ return storeRepository.findByStoreName(storeName);}

    public List<Store> getStoresByStoreNameStartingWith(String prefixStoreName){
        return storeRepository.findStoreByStoreNameStartingWith(prefixStoreName);
    }

    public List<Store> getStoresByPointRange(double firstValue ,double secondValue){
        return storeRepository.findStoreByStorePointIsBetween(firstValue,secondValue);
    }

    public Store getStoreByOwnerAccountId(long ownerAccountId){
        return storeRepository.findStoreByOwnerAccountId(ownerAccountId);
    }


    public ResponseEntity<ResponseStoreDto> addProduct(String productId, String storeId) {
        try {
            Optional<Store> storeOptional = storeRepository.findById(storeId);
            if (storeOptional.isPresent()) {
                Store updatedStore = storeOptional.get();
                List<String> productList = updatedStore.getInventoryList();
                if (productList == null) {
                    productList = new ArrayList<>();
                }
                productList.add(productId);
                updatedStore.setInventoryList(productList);
                storeRepository.save(updatedStore);

                ResponseStoreDto responseStoreDto = new ResponseStoreDto(
                        updatedStore.getId(),
                        updatedStore.getOwnerAccountId(),
                        updatedStore.getStoreName(),
                        updatedStore.getInventoryList(),
                        updatedStore.getStorePoint()
                );
                return ResponseEntity.ok(responseStoreDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public int extractUserIDFromHttpRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String userId = jwtService.extractUserId(token);
            return Integer.parseInt(userId);
        } else {
            throw new IllegalArgumentException("Authorization header is missing or invalid");
        }
    }

    public String getToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        } else {
            throw new IllegalArgumentException("Authorization header is missing or invalid");
        }
    }
}
