package com.cognixia.stagestream.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.cognixia.stagestream.config.AppConstants;
import com.cognixia.stagestream.models.User;
import com.cognixia.stagestream.services.UserService;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping(AppConstants.USER_BASE_URL)
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get all users (accessible to ADMIN role)
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID (accessible to USER and ADMIN roles)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Update user (accessible to USER and ADMIN roles)
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        // Encode password if it's updated
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        return userService.updateUser(id, userDetails);
    }

    // Delete user (accessible to ADMIN role)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}