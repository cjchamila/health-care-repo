package com.chamila.userservice.service;

import com.chamila.userservice.model.HealthCareUser;
import com.chamila.userservice.model.HealthCareUserPrinciple;
import com.chamila.userservice.repository.HealthCareUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HealthCareUserDetailsService implements UserDetailsService {

    @Autowired
    private HealthCareUserRepository healthCareUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        HealthCareUser user= healthCareUserRepository.findByusername(username);

        if (user==null) {
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }
        return new HealthCareUserPrinciple(user);
    }


}
