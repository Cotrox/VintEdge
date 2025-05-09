package com.vintedge.service;

import com.vintedge.model.User;
import com.vintedge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 🔹 POST: Creare un nuovo utente
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // 🔹 GET: Trova un utente per ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 🔹 GET: Trova un utente per username
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    // 🔹 GET: Trova un utente per email
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    // 🔹 PUT: Modifica un utente esistente
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // 🔹 DELETE: Rimuovi un utente
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
