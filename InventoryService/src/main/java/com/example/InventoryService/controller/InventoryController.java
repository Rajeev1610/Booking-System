package com.example.InventoryService.controller;

import com.example.InventoryService.dto.InventoryRequest;
import com.example.InventoryService.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @PostMapping("/validate")
    public String validate(@RequestBody List<InventoryRequest> inventoryRequests){
        inventoryService.validate(inventoryRequests);
        return "Success";
    }
}
