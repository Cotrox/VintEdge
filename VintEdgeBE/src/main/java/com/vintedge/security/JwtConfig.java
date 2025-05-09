package com.vintedge.security;

/* Configurazione del Token JWT */

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret:defaultSecretKey123456789012345678901234567890}")
    private String secretKey;

    @Value("${jwt.expiration:86400000}") // 24 ore in millisecondi
    private Long expirationTime;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Long getExpirationTime() {
        return expirationTime;
    }
}