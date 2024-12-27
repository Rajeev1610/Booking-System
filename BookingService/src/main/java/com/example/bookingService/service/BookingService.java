package com.example.bookingService.service;

import com.example.bookingService.dto.BookingRequest;

import java.util.List;

public interface BookingService {
   public List<BookingRequest> getBookingByUserId(Long id);

   public String saveBooking(List<BookingRequest> bookingRequest);
}
