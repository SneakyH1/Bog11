package com.aimane.aimaneblog.Service;

import com.aimane.aimaneblog.Model.User;
import com.aimane.aimaneblog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save a new user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find a user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Find a user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Check if the user is an admin
    public boolean isAdmin(Long userId) {
        Optional<User> user = getUserById(userId); // Get user by ID
        return user.isPresent() && "ROLE_ADMIN".equals(user.get().getRole()); // Check if role is 'ROLE_ADMIN'
    }

    // Other business logic can go here
}
