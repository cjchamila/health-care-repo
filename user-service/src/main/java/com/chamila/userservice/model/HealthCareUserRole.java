package com.chamila.userservice.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "health_care_user_roles")

@NoArgsConstructor
@Getter
@Setter
public class HealthCareUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<HealthCareUser> users;


}
