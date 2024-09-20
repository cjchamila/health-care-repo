package com.chamila.userservice.service.impl;

import com.chamila.userservice.dto.HealthCareUserDto;
import com.chamila.userservice.exception.UserRegistrationException;
import com.chamila.userservice.exception.UserServiceException;
import com.chamila.userservice.model.HealthCareUser;
import com.chamila.userservice.repository.HealthCareUserRepository;
import com.chamila.userservice.service.HealthCareUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class HealthCareUserServiceImpl implements HealthCareUserService {

    private final HealthCareUserRepository repo;
    private final ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    public HealthCareUserServiceImpl(HealthCareUserRepository repo, ObjectMapper objectMapper) {
        this.repo = repo;
        this.objectMapper = objectMapper;
    }

    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @Override
    public String saveUser(HealthCareUserDto userDto) {
        try {
            HealthCareUser user=objectMapper.convertValue(userDto,HealthCareUser.class);
            user.setPassword(encoder.encode(user.getPassword()));
            repo.save(user) ;

            return messageSource.getMessage("user.registration.success",new Object[]{user.getUsername()},
                    Locale.ENGLISH);

        }
        catch (Exception e) {
            throw new UserRegistrationException(messageSource.getMessage("user.registration.error",new Object[]{userDto.getUsername()},Locale.ENGLISH));
        }


    }
}
