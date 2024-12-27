package com.example.InventoryService.service;

import com.example.InventoryService.dto.InventoryRequest;
import com.example.InventoryService.entity.InventoryEntity;
import com.example.InventoryService.repo.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImple implements InventoryService {

    @Autowired
    InventoryRepo inventoryRepo;
    @Override
    public void validate(List<InventoryRequest> inventoryRequests) {
        for(InventoryRequest request : inventoryRequests){
            Optional<InventoryEntity> optional = inventoryRepo.findByName(request.getName());
            if(optional.isPresent()) {
                InventoryEntity inventoryEntity = optional.get();
            if(inventoryEntity.getAvailableStock() < request.getRequiredStock()){
                throw new IllegalStateException(
                        "Insufficient inventory for product id:"+request.getName()+
                                "  Available:"+inventoryEntity.getAvailableStock()
                );
            }
                inventoryEntity.setAvailableStock(inventoryEntity.getAvailableStock() - request.getRequiredStock());
            inventoryRepo.save(inventoryEntity);
            }
        }
    }
}
