package com.Scaler.BookMyShow;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;
    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date CreatedAt;

    @LastModifiedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date UpdatedAt;
}
