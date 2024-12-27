package com.example.InventoryService.service;

import com.example.InventoryService.dto.InventoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    public void validate(List<InventoryRequest>inventoryRequests);
}
