package com.Scaler.BookMyShow.Repository;

import com.Scaler.BookMyShow.Models.Show;
import com.Scaler.BookMyShow.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.font.ShapeGraphicAttribute;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Long> {
    @Override
    Optional<Show> findById(Long showId);
    @Override
    Show save(Show show);
}