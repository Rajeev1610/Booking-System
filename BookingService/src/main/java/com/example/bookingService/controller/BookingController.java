package com.example.bookingService.controller;

import com.example.bookingService.dto.BookingRequest;
import com.example.bookingService.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @GetMapping("/getBooking/{id}")
    public List<BookingRequest> getBookingByUserId(@PathVariable long id){
        return bookingService.getBookingByUserId(id);
    }
    @PostMapping("/saveBooking")
    public String saveBooking(@RequestBody List<BookingRequest> bookingRequest){
        return bookingService.saveBooking(bookingRequest);
    }
}
