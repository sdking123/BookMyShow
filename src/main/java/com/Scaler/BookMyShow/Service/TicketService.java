package com.Scaler.BookMyShow.Service;

import com.Scaler.BookMyShow.Models.Ticket;

import java.util.List;

public interface TicketService {
    Ticket bookTicket(Long userId, List<Long> showSeatId, Long showId);
    Ticket cancelTicket(Long TicketId);
    Ticket transferTicket(Long ticketId, Long fromUserId, Long toUserId);

}
