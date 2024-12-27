package com.example.InventoryService.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.InventoryService.dto.InventoryRequest;
import com.example.InventoryService.entity.InventoryEntity;
import com.example.InventoryService.repo.InventoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;


public class InventoryServiceImplTest {
    @Mock
    private InventoryRepo inventoryRepo;
    @InjectMocks
    private InventoryServiceImple inventoryService;
    private InventoryRequest validRequest;
    private InventoryRequest insufficientStockRequest;
    private InventoryRequest nonExistentRequest;

    private InventoryEntity inventoryEntity;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        validRequest = new InventoryRequest();
        validRequest.setType("Hotel");
        validRequest.setName("Hotel Taj");
        validRequest.setRequiredStock(10);
        insufficientStockRequest = new InventoryRequest();
        insufficientStockRequest.setType("Hotel");
        insufficientStockRequest.setName("Hotel Taj");
        insufficientStockRequest.setRequiredStock(20);
        nonExistentRequest = new InventoryRequest();
        nonExistentRequest.setType("Hotel");
        nonExistentRequest.setName("Hotel Taj");
        nonExistentRequest.setRequiredStock(10);
        inventoryEntity = new InventoryEntity();
        inventoryEntity.setType("Hotel");
        inventoryEntity.setName("Hotel Taj");
        inventoryEntity.setAvailableStock(15);
    }
    @Test
    public void testValidateWithSufficientStock(){
        when(inventoryRepo.findByName(validRequest.getName()))
                .thenReturn(Optional.of(inventoryEntity));
        inventoryService.validate(List.of(validRequest));
        verify(inventoryRepo,times(1)).save(inventoryEntity);
        assertEquals(5,inventoryEntity.getAvailableStock());
    }
    @Test
    public void testValidateWithInsufficientStock(){

        assertEquals(15,inventoryEntity.getAvailableStock(),"Initial Stock should be 15");
        assertEquals(20,insufficientStockRequest.getRequiredStock(),"Required stock should be 20");
         when(inventoryRepo.findByName(insufficientStockRequest.getName()))
                .thenReturn(Optional.of(inventoryEntity));
        IllegalStateException exception =assertThrows(IllegalStateException.class,()->{
            inventoryService.validate(List.of(insufficientStockRequest));
        });
        assertEquals("Insufficient inventory for product id:Hotel Taj  Available:15",exception.getMessage());
    }
    @Test
    void testValidateWithNonExistentInventory(){
        when(inventoryRepo.findByName(nonExistentRequest.getName()))
                .thenReturn(Optional.empty());
        inventoryService.validate(List.of(nonExistentRequest));
        verify(inventoryRepo,never()).save(any());
    }


}
