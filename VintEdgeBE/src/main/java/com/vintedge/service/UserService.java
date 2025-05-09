package com.vintedge.service;

import com.vintedge.model.User;
import com.vintedge.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 🔹 Ottieni tutti gli utenti
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // 🔹 Ottieni un utente per ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 🔹 Ottieni un utente per username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 🔹 Ottieni un utente per email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 🔹 Aggiungi un nuovo utente
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // 🔹 Aggiorna un utente esistente
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            user.setActive(userDetails.getActive());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 🔹 Elimina un utente per ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}