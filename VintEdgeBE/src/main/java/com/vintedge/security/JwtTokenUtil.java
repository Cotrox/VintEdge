package com.vintedge.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private String secret = "your_jwt_secret"; // Puoi mettere questa chiave in un file di configurazione

    private long expirationTime = 1000 * 60 * 60 * 10; // 10 ore

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName()) // Il subject è il nome dell'utente
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Scadenza del token
                .signWith(SignatureAlgorithm.HS512, secret) // Firma con l'algoritmo HS512
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, Authentication authentication) {
        String username = getUsernameFromToken(token);
        return username.equals(authentication.getName()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
