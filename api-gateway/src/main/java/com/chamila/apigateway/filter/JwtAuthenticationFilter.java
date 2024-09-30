package com.chamila.apigateway.filter;
import com.chamila.apigateway.service.JwtAuthenticationService;
import com.chamila.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final List<String> publicPaths = List.of("/api/v1/user/auth/**", "/api/v1/patient/public/**");
    private final AntPathMatcher pathMatcher = new AntPathMatcher();  // Use AntPathMatcher for pattern matching

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    private JwtAuthenticationService jwtAuthenticationService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestPath = exchange.getRequest().getURI().getPath();

        // Skip authentication for public paths
        if (publicPaths.stream().anyMatch(path -> pathMatcher.match(path, requestPath))) {
            return chain.filter(exchange);
        }

        // Check for Authorization header
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // For Protected endpoints : Extract the token from Authorization header
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);

        //Call user-service via REST, to get UserDetails object
        UserDetails userDetails = jwtAuthenticationService.getUserDetailsViaREST(username);

        // If the JWT exists with  username and the user is not yet authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Validate the token
            if (jwtUtil.validateToken(token, userDetails)) {
                // Set the authentication in the SecurityContext
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                // Set details (like IP address, etc.)
                ServerHttpRequest request = exchange.getRequest(); // Get the current request
                String remoteAddress = request.getRemoteAddress() != null ? request.getRemoteAddress().getAddress().getHostAddress() : "unknown";
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(exchange.getRequest()));

                // Set the authentication in the SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            }
            try {
                // Parse and validate JWT
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtUtil.generateSecretKey())
                        .parseClaimsJws(token)
                        .getBody();

                // Set claims or user details to request context (optional)
                exchange.getRequest().mutate().header("X-Authenticated-User", claims.getSubject());

            } catch (SignatureException e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // If valid, continue with the request
            return chain.filter(exchange);
        }
    }


