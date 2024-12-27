package com.example.userService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryRequest {
    private String type;
    private String name;
    private Integer requiredStock;

}
