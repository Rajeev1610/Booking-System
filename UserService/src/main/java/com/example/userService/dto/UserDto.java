package com.example.userService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String username;
    private String email;
    private List<BookingDto> bookingDto;
    private List<InventoryRequest> inventoryRequests;
}
