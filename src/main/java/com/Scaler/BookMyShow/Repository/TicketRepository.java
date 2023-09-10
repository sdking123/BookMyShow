package com.Scaler.BookMyShow.Repository;

import com.Scaler.BookMyShow.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Override
    Optional<Ticket> findById(Long ticketId);
    @Override
    Ticket save(Ticket ticket);
    }
