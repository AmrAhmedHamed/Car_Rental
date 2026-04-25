package com.example.project905.Modle;
import com.example.project905.Dto.BookACarDto;
import com.example.project905.Enum.BookCarStatus;
import com.example.project905.Modle.Car;
import com.example.project905.Modle.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Base64;
import java.util.Date;

@Entity
@Data
@Table(name = "book_a_car")
public class BookACar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long price;
    private String paymentIntentId;
    @Enumerated(EnumType.STRING)
    private BookCarStatus bookCarStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Car car;

    public BookACarDto getBookACarDto() {
        BookACarDto dto = new BookACarDto();
        dto.setId(this.id);
        dto.setFromDate(this.fromDate);
        dto.setToDate(this.toDate);
        dto.setDays(this.days);
        dto.setPrice(this.price); 
        dto.setBookCarStatus(this.bookCarStatus);
        dto.setPaymentIntentId(this.paymentIntentId);
        dto.setUserId(this.user.getId());
        dto.setUsername(this.user.getUsername());
        dto.setCarId(this.car.getId());
        dto.setCarName(this.car.getBrand() + " " + this.car.getName());

        if (this.car.getImage() != null) {
            dto.setCarImage(Base64.getEncoder().encodeToString(this.car.getImage()));
        }
        return dto;
    }
}


