package com.chamila.userservice.controller;

import com.chamila.userservice.dto.HealthCareUserDto;
import com.chamila.userservice.dto.response.LoginResponse;
import com.chamila.userservice.model.HealthCareUser;
import com.chamila.userservice.service.HealthCareUserService;
import com.chamila.userservice.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private HealthCareUserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("user/auth/register")
    public String register(@RequestBody  HealthCareUserDto user) {
        return service.saveUser(user);
    }

    @PostMapping("user/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody HealthCareUser user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUsername());
            return new ResponseEntity<>(new LoginResponse(token,"Success!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new LoginResponse(String.valueOf(HttpStatus.UNAUTHORIZED),"Invalid credentials!"), HttpStatus.UNAUTHORIZED);
        }
    }


}
