package com.gateway.router.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    // Use the same secret key used by AUTH-SERVICE
    private final String secret = "mySecretKeyMySecretKeyMySecretKey123456";

    private final Key key = Keys.hmacShaKeyFor(
            secret.getBytes(StandardCharsets.UTF_8)
    );

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String path = exchange.getRequest()
                .getURI()
                .getPath();

        System.out.println("JWT FILTER CALLED: " + path);

        // Allow authentication endpoints without JWT
        if (path.startsWith("/auth/login")
                || path.startsWith("/auth/register")
                || path.startsWith("/auth/test")) {

            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // Authorization header missing
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            System.out.println("JWT TOKEN MISSING");

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println("JWT VALID");
            System.out.println("Username: " + claims.getSubject());

            // JWT is valid
            return chain.filter(exchange);

        } catch (Exception e) {

            System.out.println("JWT INVALID: " + e.getMessage());

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -100;
    }
}