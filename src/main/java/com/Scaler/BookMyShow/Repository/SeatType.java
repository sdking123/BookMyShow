package com.Scaler.BookMyShow.Repository;

import com.Scaler.BookMyShow.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatType extends JpaRepository<SeatType, Long> {
    @Override
    Optional<SeatType> findById(Long showId);
    @Override
    SeatType save(SeatType show);
}
