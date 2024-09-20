package com.chamila.userservice.model;


import jakarta.persistence.*;
import lombok.*;



@Table(name = "health_care_user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HealthCareUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

}
