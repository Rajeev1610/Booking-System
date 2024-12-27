package com.example.userService.service;

import com.example.userService.dto.BookingDto;
import com.example.userService.dto.InventoryRequest;
import com.example.userService.dto.UserDto;
import com.example.userService.entity.UserEntity;
import com.example.userService.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    RestTemplate restTemplate;

    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    @Override

    public UserDto getBookingByUserId(Long id) {
        Optional<UserEntity> optional = userRepo.findById(id);
        UserDto userDto = new UserDto();
        if (optional.isPresent()) {
            userDto.setEmail(optional.get().getEmail());
            userDto.setUsername(optional.get().getUsername());
            userDto.setBookingDto(getBookingDetails(id));
        }
        return userDto;
    }

    public UserDto getUserByIdFallback(Long id) {
        Optional<UserEntity> optional = userRepo.findById(id);
        UserDto userDto = new UserDto();
        if (optional.isPresent()) {
            userDto.setEmail(optional.get().getEmail());
            userDto.setUsername(optional.get().getUsername());
        }
        return userDto;
    }

    @Override
    public String createOrder(UserDto userDto) {
        saveBooking(userDto.getBookingDto());
        List<InventoryRequest> inventoryRequests = userDto.getInventoryRequests();
        if (inventoryRequests == null || inventoryRequests.isEmpty()) {
            throw new IllegalArgumentException("Inventory update request cannot be empty");
        }
        validateInventory(inventoryRequests);
        return "Success";
    }

    public List<BookingDto> getBookingDetails(Long userId) {
        String url = "http://localhost:8080/booking/getBooking/" + userId;
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            return Objects.requireNonNull(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to update booking:" + e.getMessage(), e);
        }
    }

    public String saveBooking(List<BookingDto> bookingDto) {
        String url = "http://localhost:8080/booking/saveBooking";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, bookingDto, String.class);
            return "Success";
        } catch (Exception e) {
            throw new RuntimeException("Failed to update booking:" + e.getMessage(), e);
        }
    }

    public String validateInventory(List<InventoryRequest> inventoryRequest) {
        String url = "http://localhost:8082/inventory/validate";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, inventoryRequest, String.class);
            return "Success";
        } catch (Exception e) {
            throw new RuntimeException("Failed to update booking:" + e.getMessage(), e);
        }
    }

}
