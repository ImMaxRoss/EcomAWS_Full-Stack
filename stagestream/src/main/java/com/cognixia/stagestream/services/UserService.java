package com.cognixia.stagestream.services;

import com.cognixia.stagestream.repositories.CartRepository;
import com.cognixia.stagestream.repositories.UserRepository;
import com.cognixia.stagestream.models.Cart;
import com.cognixia.stagestream.models.User;
import com.cognixia.stagestream.exceptions.ResourceNotFoundException;
import com.cognixia.stagestream.config.AppConstants;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;
    

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                             .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    @PreAuthorize("hasRole(T(com.cognixia.stagestream.config.AppConstants).ADMIN_ROLE)")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PreAuthorize("hasRole(T(com.cognixia.stagestream.config.AppConstants).USER_ROLE) or hasRole(T(com.cognixia.stagestream.config.AppConstants).ADMIN_ROLE)")
    public User getUserById(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional
    @PreAuthorize("hasRole(T(com.cognixia.stagestream.config.AppConstants).USER_ROLE) or hasRole(T(com.cognixia.stagestream.config.AppConstants).ADMIN_ROLE)")
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
    
        return userRepository.save(user);
    }

    @Transactional
    @PreAuthorize("hasRole(T(com.cognixia.stagestream.config.AppConstants).ADMIN_ROLE)")
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Transactional
    public User registerUser(User user) {
        user.setRole(AppConstants.USER_ROLE);

        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(savedUser); 
        cart.setTotalPrice(0.0);

        Cart savedCart = cartRepository.save(cart);


        savedUser.setCart(savedCart);

        savedUser = userRepository.save(savedUser);

        return savedUser;
    }

}