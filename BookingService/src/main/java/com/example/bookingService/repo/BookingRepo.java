package com.example.bookingService.repo;

import com.example.bookingService.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface BookingRepo extends JpaRepository<BookingEntity,Long> {

    List<BookingEntity> findByUserId(Long userId);

}
