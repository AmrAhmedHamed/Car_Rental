package com.example.project905.Modle;
import com.example.project905.Dto.BuyACarDto;
import com.example.project905.Enum.BuyCarStatus;
import com.example.project905.Modle.Car;
import com.example.project905.Modle.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Base64;
import java.util.Date;

@Entity
@Data
@Table(name = "buy_car")
public class BuyCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date buyDate;
    private Long price;

    @Enumerated(EnumType.STRING)
    private BuyCarStatus status;
    private String paymentIntentId;


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

    public BuyACarDto getBuyACarDto() {
        BuyACarDto dto = new BuyACarDto();
        dto.setId(this.id);
        dto.setBuyDate(this.buyDate);
        dto.setPrice(this.price);
        dto.setStatus(this.status != null ? this.status.name() : null);
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