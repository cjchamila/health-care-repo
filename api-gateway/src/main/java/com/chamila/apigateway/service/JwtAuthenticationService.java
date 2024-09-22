package com.chamila.apigateway.service;
import com.chamila.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class JwtAuthenticationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${services.user-service-url}")
    private String userServiceUrl;

    public UserDetails getUserDetailsViaREST(String token) {
        String username = jwtUtil.extractUsername(token);

        if (username != null) {
            // Call User Service via REST to get user details
            return restTemplate.getForObject(userServiceUrl, UserDetails.class);
        }

        return null;
    }




}
