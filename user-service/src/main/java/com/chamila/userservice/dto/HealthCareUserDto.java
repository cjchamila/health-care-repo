package com.chamila.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthCareUserDto {

    @JsonIgnore
    private Long userId;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private Set<HealthCareUserRoleDto> roles; // Use RoleDto instead of Role entity

}



