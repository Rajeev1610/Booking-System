package com.example.bookingService.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDateTime;

@Getter
@Setter
public class BookingRequest {

    public Long id;
    public Long userId;
    public String type;
    public String details;
    private LocalDateTime bookingDate;
}
