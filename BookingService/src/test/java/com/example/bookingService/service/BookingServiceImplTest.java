package com.example.bookingService.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.bookingService.dto.BookingRequest;
import com.example.bookingService.entity.BookingEntity;
import com.example.bookingService.repo.BookingRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {
    @Mock
    private BookingRepo bookingRepo;
    @InjectMocks
    private BookingServiceImpl bookingService;

    private BookingEntity bookingEntity;

    private BookingRequest bookingRequest;

    @BeforeEach
    void setup(){
        bookingEntity = new BookingEntity();
        bookingEntity.setId(1L);
        bookingEntity.setUserId(1L);
        bookingEntity.setType("Hotel");
        bookingEntity.setDetails("Hotel for a week");
        bookingEntity.setBookingDate(LocalDateTime.now());
        bookingRequest = new BookingRequest();
        bookingRequest.setId(1L);
        bookingRequest.setUserId(1L);
        bookingRequest.setType("Hotel");
        bookingRequest.setDetails("Hotel for a week");
    }
    @Test
    void testGetBookingByUserId(){
        when(bookingRepo.findByUserId(1L))
                .thenReturn(Arrays.asList(bookingEntity));
        List<BookingRequest> result = bookingService.getBookingByUserId(1L);
        assertEquals(1,result.size());
        assertEquals("Hotel",result.get(0).type);
        assertEquals("Hotel for a week",result.get(0).details);
        verify(bookingRepo,times(1)).findByUserId(1L);
    }
    @Test
    void testSaveBooking(){
        List<BookingRequest> requests = Arrays.asList(bookingRequest);
        when(bookingRepo.saveAll(anyList()))
                .thenReturn(Arrays.asList(bookingEntity));
        String result = bookingService.saveBooking(requests);
        assertEquals("Success",result);
        verify(bookingRepo,times(1)).saveAll(anyList());
    }
}
