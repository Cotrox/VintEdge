package com.vintedge.security;

import java.security.SecureRandom;
import java.util.Base64;

/* Run this file only to generate a random secret key for token JWT */

public class SecretKeyGenerator {
    public static void main(String[] args) {
        System.out.println("Debug: main method started");
        byte[] randomBytes = new byte[64];
        new SecureRandom().nextBytes(randomBytes);
        String secretKey = Base64.getEncoder().encodeToString(randomBytes);
        System.out.println(secretKey);
    }
}
