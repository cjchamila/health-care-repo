package com.chamila.userservice.service;

import com.chamila.userservice.dto.HealthCareUserDto;
import com.chamila.userservice.model.HealthCareUser;

public interface HealthCareUserService {
    public String saveUser(HealthCareUserDto user);
    public HealthCareUser findByUserName(String userName);
}
