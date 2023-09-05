package com.Scaler.BookMyShow.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Screen extends BaseModel{
    private String name;
    @OneToMany
    private List<Seats> seats;
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection //mapping table for Screen to features
    private List<Features> features;

}
