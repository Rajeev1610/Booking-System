package com.example.userService.service;

import com.example.userService.dto.UserDto;
import com.example.userService.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public List<UserEntity> getAllUsers();

    public UserDto getBookingByUserId(Long id);

    public String createOrder(UserDto userDto);

    UserDto getUserByIdFallback(Long id);
}
