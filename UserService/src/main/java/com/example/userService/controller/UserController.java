package com.example.userService.controller;

import com.example.userService.config.JwtUtil;
import com.example.userService.dto.UserDto;
import com.example.userService.entity.UserEntity;
import com.example.userService.repo.UserRepo;
import com.example.userService.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;


    public UserController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/login")
    public String login(@RequestParam String userName){
        return jwtUtil.generateToken(userName);
    }

    @GetMapping("/getAll")
    public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getUser/{id}")
    @CircuitBreaker(name="bookingservice",fallbackMethod = "getUserByIdFallback")
    public UserDto getUserById(@PathVariable Long id){
        return userService.getBookingByUserId(id);
    }

    @PostMapping("/save")
    @CircuitBreaker(name="bookingservice,inventoryservice",fallbackMethod = "saveInventoryFallback")
    public String saveInventory(@RequestBody UserDto userDto){
        return userService.createOrder(userDto);
    }

    public UserDto getUserByIdFallback(Long id,Throwable ex){
        System.out.println("Enter");
       return userService.getUserByIdFallback(id);
    }

    public String saveInventoryFallback(UserDto userDto,Throwable ex){
        return "Service not available";
    }
}
