package com.example.bookingService.service;

import com.example.bookingService.dto.BookingRequest;
import com.example.bookingService.entity.BookingEntity;
import com.example.bookingService.repo.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepo bookingRepo;

    @Override
    /*    Cacheable(value = "bookings", key = "#id")*/
    public List<BookingRequest> getBookingByUserId(Long id) {
        List<BookingEntity> bookingEntity = bookingRepo.findByUserId(id);
        List<BookingRequest> bookingRequests = new ArrayList<>();
        for (BookingEntity bookingEntity1 : bookingEntity) {
            BookingRequest bookingRequest = new BookingRequest();
            bookingRequest.setUserId(id);
            bookingRequest.setId(bookingEntity1.getId());
            bookingRequest.setType(bookingEntity1.getType());
            bookingRequest.setBookingDate(bookingEntity1.getBookingDate());
            bookingRequest.setDetails(bookingEntity1.getDetails());
            bookingRequests.add(bookingRequest);
        }
        return bookingRequests;
    }

    @Override
    public String saveBooking(List<BookingRequest> bookingRequest) {
        List<BookingEntity> bookingEntitys = new ArrayList<>();
        for (BookingRequest bookingRequest1 : bookingRequest) {
            BookingEntity bookingEntity = new BookingEntity();
            bookingEntity.setType(bookingRequest1.getType());
            bookingEntity.setDetails(bookingRequest1.getDetails());
            bookingEntity.setUserId(bookingRequest1.getUserId());
            bookingEntity.setBookingDate(LocalDateTime.now());
            bookingEntitys.add(bookingEntity);
        }
        List<BookingEntity> saved = bookingRepo.saveAll(bookingEntitys);
        return "Success";
    }

/*    *//*   @Caching(evict = {
               @CacheEvict(value = "bookings",key="#userId",condition = "#userId != null"),
               @CacheEvict(value = "bookings",allEntries = true,condition = "#userId == null")
       })*//*
    public void clearBookingCache(Long userId) {

    }*/
}
