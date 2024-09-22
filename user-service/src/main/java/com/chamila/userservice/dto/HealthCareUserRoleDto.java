package com.chamila.userservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthCareUserRoleDto {

    @JsonIgnore
    private Long roleId;
    private String name;
    private String description;


    @JsonCreator
    public HealthCareUserRoleDto(String name) {
        this.name = name;
    }
}

