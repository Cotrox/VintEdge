package com.vintedge.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token has expired: {}", e.getMessage());
            return null;
        } catch (UnsupportedJwtException e) {
            logger.error("JWT Token is unsupported: {}", e.getMessage());
            return null;
        } catch (MalformedJwtException e) {
            logger.error("JWT Token is malformed: {}", e.getMessage());
            return null;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Error while getting username from token: {}", e.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);
            String username = getUsernameFromToken(token);
            return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
            logger.warn("JWT Token has expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warn("JWT Token is unsupported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("JWT Token is malformed: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error while validating token: {}", e.getMessage());
        }
        return false;
    }

    private boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            logger.warn("JWT Token has expired (in isTokenExpired): {}", e.getMessage());
            return true; // Consider expired if exception, handle in validateToken
        } catch (UnsupportedJwtException e) {
            logger.warn("JWT Token is unsupported (in isTokenExpired): {}", e.getMessage());
            return true;
        } catch (MalformedJwtException e) {
            logger.warn("JWT Token is malformed (in isTokenExpired): {}", e.getMessage());
            return true;
        } catch (SignatureException e) {
            logger.warn("Invalid JWT signature (in isTokenExpired): {}", e.getMessage());
            return true;
        } catch (IllegalArgumentException e) {
            logger.warn("JWT claims string is empty (in isTokenExpired): {}", e.getMessage());
            return true;
        } catch (Exception e) {
            logger.error("Error while checking if token is expired: {}", e.getMessage());
            return true;
        }
    }
}