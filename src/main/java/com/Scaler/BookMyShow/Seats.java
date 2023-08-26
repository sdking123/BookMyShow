package com.Scaler.BookMyShow;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Seats extends BaseModel{
    private int row;
    private int col;
    private String seatNumber;
    @ManyToOne
    private SeatType seatType;

}
