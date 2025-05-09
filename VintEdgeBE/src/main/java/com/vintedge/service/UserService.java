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
        return userRepository.getUserById(id);
    }

    // 🔹 Ottieni un utente per username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    // 🔹 Ottieni un utente per email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    // 🔹 Aggiungi un nuovo utente
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // 🔹 Aggiorna un utente esistente
    public User updateUser(Long id, User userDetails) {
        return userRepository.getUserById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            user.setPhone(userDetails.getPhone());
            user.setName(userDetails.getName());
            user.setSurname(userDetails.getSurname());
            user.setBirth(userDetails.getBirth());
            user.setAddress(userDetails.getAddress());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 🔹 Elimina un utente per ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}