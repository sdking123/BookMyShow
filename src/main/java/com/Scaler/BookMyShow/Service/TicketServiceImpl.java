package com.Scaler.BookMyShow.Service;

import com.Scaler.BookMyShow.Exception.ShowSeatNotAvailableException;
import com.Scaler.BookMyShow.Exception.TicketNotFoundException;
import com.Scaler.BookMyShow.Exception.UserNotFound;
import com.Scaler.BookMyShow.Models.*;
import com.Scaler.BookMyShow.Repository.ShowRepository;
import com.Scaler.BookMyShow.Repository.ShowSeatRepository;
import com.Scaler.BookMyShow.Repository.TicketRepository;
import com.Scaler.BookMyShow.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private ShowRepository showRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(Long userId, List<Long> showSeatIds, Long showId) {
        User bookedbyUser = userRepository.findById(userId).get();
        Show show = showRepository.findById(showId).get();

        for (Long showSeatId : showSeatIds) {
            ShowSeat showSeat = showSeatRepository.findById(showSeatId).get();
            if (showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) {
                showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            } else {
                throw new ShowSeatNotAvailableException("Show seat is not available");
            }
            showSeatRepository.save(showSeat);

        }
        boolean paymentDone = paymentCheck();
        List<ShowSeat> showSeats = new ArrayList<>();
        double amount = 0;
        if (paymentDone) {
            for (Long showSeatId : showSeatIds) {
                ShowSeat showSeat = showSeatRepository.findById(showSeatId).get();
                showSeat.setShowSeatStatus(ShowSeatStatus.BOOKED);
                showSeat = showSeatRepository.save(showSeat);
                showSeats.add(showSeat);
                amount = amount + showSeat.getPrice();
            }
        }

        return ticketGenerator(bookedbyUser, show, showSeats, amount);
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
    private boolean paymentCheck(){
        return true;
    }

    private Ticket ticketGenerator(User user, Show show, List<ShowSeat> showSeats,double amount){
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setShowSeats(showSeats);
        ticket.setAmount(amount);
        ticket.setBookedAt(LocalDateTime.now());
        ticket.setBookingStatus(BookingStatus.CONFIRMED);
        return ticketRepository.save(ticket);
    }
}


