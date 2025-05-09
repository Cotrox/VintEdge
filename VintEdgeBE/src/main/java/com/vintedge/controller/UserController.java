package com.vintedge.controller;

import com.vintedge.model.User;
import com.vintedge.security.JwtTokenProvider;
import com.vintedge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // Registrazione utente
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        return userService.registerUser(user);
    }

    // Login utente e generazione token JWT
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok("Bearer " + jwt);
    }

    // Recupero dei dati utente autenticato
    @GetMapping("/user")
    public ResponseEntity<User> getUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserInfo(username);
        return ResponseEntity.ok(user);
    }
}
