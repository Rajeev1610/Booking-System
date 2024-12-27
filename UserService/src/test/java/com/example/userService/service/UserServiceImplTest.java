package com.example.userService.service;

import com.example.userService.dto.BookingDto;
import com.example.userService.dto.InventoryRequest;
import com.example.userService.dto.UserDto;
import com.example.userService.entity.UserEntity;
import com.example.userService.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private RestTemplate restTemplate;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAllUsers(){
        List<UserEntity> mockUsers = Arrays.asList(new UserEntity(1L,"User1","user1@gmail.com"),
                new UserEntity(2L,"User2","user2@gmail.com"));
        when(userRepo.findAll()).thenReturn(mockUsers);
        List<UserEntity> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals("user1@gmail.com",result.get(0).getEmail());
        verify(userRepo,times(1)).findAll();
    }
    @Test
    void testGetBookingByUserId(){
        UserEntity userEntity = new UserEntity(1L,"user1","user1@gmail.com");
        when(userRepo.findById(1L)).thenReturn(Optional.of(userEntity));
        when(restTemplate.getForEntity(anyString(),eq(List.class))).
                thenReturn(ResponseEntity.ok(Arrays.asList(new BookingDto())));
        UserDto result = userService.getBookingByUserId(1L);
        assertNotNull(result.getBookingDto());
        assertEquals("user1",result.getUsername());
        assertNotNull(result.getBookingDto());
        verify(userRepo,times(1)).findById(1L);
        verify(restTemplate,times(1)).getForEntity(anyString(),eq(List.class));
    }
    @Test
    void testGetUserByIdFallback(){
        UserEntity userEntity = new UserEntity(1L,"user1","user1@gmail.com");
        when(userRepo.findById(1L)).thenReturn(Optional.of(userEntity));
        UserDto result = userService.getUserByIdFallback(1L);
        assertNotNull(result);
        assertEquals("user1@gmail.com",result.getEmail());
        verify(userRepo,times(1)).findById(1L);
    }
    @Test
    void testCreateOrderSuccess(){
        BookingDto bookingDto = new BookingDto();
        InventoryRequest inventoryRequest = new InventoryRequest();
        UserDto userDto = new UserDto();
        userDto.setBookingDto(List.of(bookingDto));
        userDto.setInventoryRequests(List.of(inventoryRequest));
        doReturn("Success").when(userService).saveBooking(anyList());
        doReturn("Success").when(userService).validateInventory(anyList());
        String result = userService.createOrder(userDto);
        assertEquals("Success",result);
        verify(userService,times(1)).saveBooking(anyList());
        verify(userService,times(1)).validateInventory(anyList());
    }
    @Test
    void testCreateOrderEmptyInventory(){
        UserDto userDto = new UserDto();
        userDto.setBookingDto(List.of(new BookingDto()));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() ->userService.createOrder(userDto));
        assertEquals("Inventory update request cannot be empty",exception.getMessage());
    }
    @Test
    void testGetBookingDetails(){
        when(restTemplate.getForEntity(anyString(),eq(List.class))).
                thenReturn(ResponseEntity.ok(Arrays.asList(new BookingDto())));
        List<BookingDto> result = userService.getBookingDetails(1L);
        assertNotNull(result);
        assertEquals(1,result.size());
        verify(restTemplate,times(1)).getForEntity(anyString(),eq(List.class));
    }
    @Test
    void testSaveBooking(){
        when(restTemplate.postForEntity(anyString(),anyList(),eq(String.class))).
                thenReturn(ResponseEntity.ok("Success"));
        String result = userService.saveBooking(List.of(new BookingDto()));
        assertEquals("Success",result);
        verify(restTemplate,times(1)).postForEntity(anyString(),anyList(),eq(String.class));
    }
    @Test
    void testValidateInventory(){
        when(restTemplate.postForEntity(anyString(),anyList(),eq(String.class))).
                thenReturn(ResponseEntity.ok("Success"));
        String result = userService.validateInventory(List.of(new InventoryRequest()));
        assertEquals("Success",result);
        verify(restTemplate,times(1)).postForEntity(anyString(),anyList(),eq(String.class));
    }
}
