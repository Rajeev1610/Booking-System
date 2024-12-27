package com.example.userService.controller;

import com.example.userService.config.JwtUtil;
import com.example.userService.dto.UserDto;
import com.example.userService.entity.UserEntity;
import com.example.userService.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testLogin() throws Exception {
        String userName = "testUser";
        String mockToken = "mockToken";
        when(jwtUtil.generateToken(userName)).thenReturn(mockToken).toString();
        mockMvc.perform(post("/user/login").param("userName", userName)).andExpect(status().isOk())
                .andExpect(content().string(mockToken));
        verify(jwtUtil, times(1)).generateToken(userName);
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<UserEntity> mockUsers = Arrays.asList(new UserEntity(1L, "user1", "user1@gmail"),
                new UserEntity(2L, "user2", "user2@gmail"));
        when(userService.getAllUsers()).thenReturn(mockUsers);
        mockMvc.perform(get("/user/getAll")).andExpect(status().isOk())
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[0].email").value("user1@gmail"))
                .andExpect(jsonPath("$[1].username").value("user2"))
                .andExpect(jsonPath("$[1].email").value("user2@gmail"));
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() throws Exception {
        Long userId = 1l;
        UserDto mockUserDto = new UserDto();
        mockUserDto.setUsername("testUser");
        mockUserDto.setEmail("testUser@gmail.com");
        when(userService.getBookingByUserId(userId)).thenReturn(mockUserDto);
        mockMvc.perform(get("/user/getUser/{id}", userId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.email").value("testUser@gmail.com"));
        verify(userService, times(1)).getBookingByUserId(userId);
    }

    @Test
    void testGetUserByIdFallback() throws Exception {
        Long userId = 1L;
        UserDto mockUserDto = new UserDto();
        mockUserDto.setUsername("fallbackUser");
        mockUserDto.setEmail("fallbackUser@gmail.com");
        when(userService.getBookingByUserId(userId)).thenReturn(mockUserDto);
        mockMvc.perform(get("/user/getUser/{id}", userId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("fallbackUser"))
                .andExpect(jsonPath("$.email").value("fallbackUser@gmail.com"));
        verify(userService, times(1)).getBookingByUserId(userId);
    }

    @Test
    void testSaveInventory() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        String responseMessage = "Success";
        when(userService.createOrder(any(UserDto.class))).thenReturn(responseMessage);
        mockMvc.perform(post("/user/save").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"testUser\"}"))
                .andExpect(status().isOk()).andExpect(content().string(responseMessage));
        verify(userService, times(1)).createOrder(any(UserDto.class));
    }

    @Test
    void testSaveInventoryFallback() throws Exception {
        Throwable th = new Throwable();
        String res = userController.saveInventoryFallback(new UserDto(), th);
        Assertions.assertEquals(res,"Service not available");
    }

}
