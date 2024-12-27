package com.example.bookingService.controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import com.example.bookingService.dto.BookingRequest;
import com.example.bookingService.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;
    private BookingRequest bookingRequest;

    @BeforeEach
    void setup(){
        bookingRequest = new BookingRequest();
        bookingRequest.id =1L;
        bookingRequest.userId = 1L;
        bookingRequest.type = "Hotel";
        bookingRequest.details = "Hotel for a week";
    }
    @Test
    void testGetBookingByUserId()throws Exception{
        List<BookingRequest> bookingRequests = Arrays.asList(bookingRequest);
        when(bookingService.getBookingByUserId(1L)).thenReturn(bookingRequests);
        mockmvc.perform(get("/booking/getBooking/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].userId",is(1)))
                .andExpect(jsonPath("$[0].type",is("Hotel")))
                .andExpect(jsonPath("$[0].details",is("Hotel for a week")));
        verify(bookingService,times(1)).getBookingByUserId(1L);
    }
    @Test
    void testSaveBooking()throws Exception{
        List<BookingRequest> bookingRequests = Arrays.asList(bookingRequest);
        when(bookingService.saveBooking(anyList())).thenReturn("Success");
        mockmvc.perform(post("/booking/saveBooking").contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(bookingRequests)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
        verify(bookingService,times(1)).saveBooking(anyList());
    }
}
