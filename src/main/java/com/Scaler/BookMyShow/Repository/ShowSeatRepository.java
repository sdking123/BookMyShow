package com.Scaler.BookMyShow.Repository;

import com.Scaler.BookMyShow.Models.ShowSeat;
import com.Scaler.BookMyShow.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    @Override
    Optional<ShowSeat> findById(Long showSeatId);
    @Override
    ShowSeat save(ShowSeat showSeat);
}
