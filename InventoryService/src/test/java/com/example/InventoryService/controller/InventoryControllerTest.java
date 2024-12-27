package com.example.InventoryService.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.example.InventoryService.dto.InventoryRequest;
import com.example.InventoryService.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

public class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;
    @InjectMocks
    private InventoryController inventoryController;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    }

    @Test
    void testValidate()throws Exception{
        String jsonRequest = "[{\"type\":\"Hotel\",\"name\":\"HotelTaj\",\"requiredStock\":10}]";
        InventoryRequest request1 = new InventoryRequest();
        request1.setType("Hotel");
        request1.setName("HotelTaj");
        request1.setRequiredStock(5);

        List<InventoryRequest> request = List.of(request1);
        doNothing().when(inventoryService).validate(request);
        mockMvc.perform(post("/inventory/validate").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
        verify(inventoryService,times(1)).validate(argThat(argument -> {
            InventoryRequest request2 = argument.get(0);
            return "Hotel".equals(request2.getType())
                    && "HotelTaj".equals(request2.getName())
                    && request2.getRequiredStock() ==10;
        }));
    }

}
