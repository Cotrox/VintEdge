package com.vintedge.service;

import com.vintedge.model.User;
import com.vintedge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder; // Importa PasswordEncoder

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Inietta PasswordEncoder

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) { // Costruttore con
                                                                                         // PasswordEncoder
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔹POST: Creare un nuovo utente
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash della password prima di salvarla
        return userRepository.save(user);
    }

    // 🔹GET: Trova un utente per ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 🔹GET: Trova un utente per username
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    // 🔹GET: Trova un utente per email
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    // 🔹PUT: Modifica un utente esistente
    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash della password prima di salvarla
        return userRepository.save(user);
    }

    // 🔹DELETE: Rimuovi un utente
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Spring Security: Caricamento utente per l'autenticazione
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Confronta la password fornita con l'hash nel database
        // **IMPORTANTE: Non abbiamo accesso alla password fornita dall'utente qui.
        // Questo metodo *non* riceve la password in chiaro.
        // La password in chiaro viene gestita dall'AuthenticationManager.**
        // Invece, restituiamo l'utente con la password hashata. L'AuthenticationManager
        // si occuperà di confrontare
        // la password inviata dall'utente (che *lui* ha) con l'hash qui.

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // Restituisci la password hashata
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())) // Aggiungi il ruolo
        );
    }
}