package com.Scaler.BookMyShow;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Ticket extends BaseModel{
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;
    @ManyToOne
    private Show show;
    @OneToMany
    private List<Payment> payments;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<ShowSeat> showSeats;
    private Date BookedAt;
    private int amount;

}

