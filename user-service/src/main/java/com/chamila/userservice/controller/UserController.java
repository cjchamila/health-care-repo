package com.chamila.userservice.controller;

import com.chamila.userservice.dto.HealthCareUserDto;
import com.chamila.userservice.model.HealthCareUser;
import com.chamila.userservice.service.HealthCareUserService;
import com.chamila.userservice.service.JwtService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private HealthCareUserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("register")
    public String register(@RequestBody  HealthCareUserDto user) {
        return service.saveUser(user);
    }

    @PostMapping("login")
    public String login(HealthCareUser user){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());
        else
            return "Login Failed";

    }

}
