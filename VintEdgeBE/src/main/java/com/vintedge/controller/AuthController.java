package com.vintedge.controller;

import com.vintedge.model.AuthenticationRequest;
import com.vintedge.model.AuthenticationResponse;
import com.vintedge.security.JwtTokenUtil;
import com.vintedge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService,
            JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public AuthenticationResponse createJwtToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {

        try {
            // Autenticazione dell'utente
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));

            // Se le credenziali sono corrette, aggiungiamo l'autenticazione nel contesto
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generiamo il JWT per l'utente autenticato
            String token = jwtTokenUtil.generateToken(authentication);

            // Restituiamo la risposta con il token
            return new AuthenticationResponse(token);
        } catch (Exception e) {
            throw new Exception("Invalid username or password", e);
        }
    }
}
