package com.example.project905.Modle;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users-car")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String username;

    private String password;
    private Integer age;
    private String role;


    public User(String username, String password, Integer age, String role) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.role = role;
    }




}
