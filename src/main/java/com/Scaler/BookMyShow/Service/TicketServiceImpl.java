package com.Scaler.BookMyShow.Service;

import com.Scaler.BookMyShow.Exception.TicketNotFoundException;
import com.Scaler.BookMyShow.Exception.UserNotFound;
import com.Scaler.BookMyShow.Models.*;
import com.Scaler.BookMyShow.Repository.ShowSeatRepository;
import com.Scaler.BookMyShow.Repository.TicketRepository;
import com.Scaler.BookMyShow.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket bookTicket(Long userId, List<Long> showSeatId, Long showId) {
        return null;
    }

    @Override
    public Ticket cancelTicket(Long ticketId) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if(ticketOptional.isEmpty()){
            throw new TicketNotFoundException("Ticket for given ID is not found");
        }
        Ticket ticket = ticketOptional.get();
        ticket.setBookingStatus(BookingStatus.CANCELLED);
        for(ShowSeat showSeat : ticket.getShowSeats()){
            showSeat.setShowSeatStatus(ShowSeatStatus.AVAILABLE);
            showSeatRepository.save(showSeat);
        }
        ticketRepository.save(ticket);
        for(Payment payment : ticket.getPayments()){
            payment.getRefNo();
            //Send the ref no to 3rd party for refund
        }
        return ticket;
    }

    @Override
    public Ticket transferTicket(Long ticketId, Long fromUserId, Long toUserId) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if(ticketOptional.isEmpty()){
            throw new TicketNotFoundException("Ticket for given ID is not found");
        }
        Optional<User> fromUserOptional = userRepository.findById(fromUserId);
        Optional<User> toUserOptional = userRepository.findById(toUserId);

        if(fromUserOptional.isEmpty() || toUserOptional.isEmpty()){
            throw new UserNotFound("Given user details for ticket transfer is not found");
        }
        Ticket ticket = ticketOptional.get();
        User fromUser = fromUserOptional.get();
        List<Ticket> bookedTicketHistory = fromUser.getTickets();
        bookedTicketHistory.remove(ticket);
        userRepository.save(fromUser);

        User toUser = toUserOptional.get();
        bookedTicketHistory = fromUser.getTickets();
        bookedTicketHistory.add(ticket);
        toUser = userRepository.save(toUser);


        ticket.setUser(toUser);
        return ticketRepository.save(ticket);

    }
}


