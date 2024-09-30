package com.chamila.userservice.service.impl;

import com.chamila.userservice.dto.HealthCareUserDto;
import com.chamila.userservice.exception.UserRegistrationException;
import com.chamila.userservice.model.HealthCareUser;
import com.chamila.userservice.model.HealthCareUserRole;
import com.chamila.userservice.repository.HealthCareUserRepository;
import com.chamila.userservice.repository.HealthCareUserRoleRepository;
import com.chamila.userservice.service.HealthCareUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class HealthCareUserServiceImpl implements HealthCareUserService {

    private final HealthCareUserRepository userRepo;
    private final HealthCareUserRoleRepository userRoleRepo;
    private final ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    public HealthCareUserServiceImpl(HealthCareUserRepository userRepo,
                                     HealthCareUserRoleRepository userRoleRepo,
                                     ObjectMapper objectMapper) {
        this.userRoleRepo = userRoleRepo;
        this.userRepo=userRepo;
        this.objectMapper = objectMapper;
    }

    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @Override
    public String saveUser(HealthCareUserDto userDto) {
        try {
            Set<HealthCareUserRole> roles=new HashSet<>();
            HealthCareUser user=objectMapper.convertValue(userDto,HealthCareUser.class);
            user.setPassword(encoder.encode(user.getPassword()));
            user.getRoles().stream().forEach(role->{
                HealthCareUserRole userRole = new HealthCareUserRole();
                userRole = userRoleRepo.findByName(role.getName());
                roles.add(userRole);
            });

            user.setRoles(roles);
            userRepo.save(user) ;

            return messageSource.getMessage("user.registration.success",new Object[]{user.getUsername()},
                    Locale.ENGLISH);

        }
        catch (Exception e) {
            throw new UserRegistrationException(messageSource.getMessage("user.registration.error",new Object[]{userDto.getUsername()},Locale.ENGLISH));
        }


    }

    @Override
    public HealthCareUser findByUserName(String userName) {
        return userRepo.findByusername(userName);
    }
}
