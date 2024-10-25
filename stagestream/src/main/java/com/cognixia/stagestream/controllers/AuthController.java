package com.cognixia.stagestream.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.cognixia.stagestream.models.User;
import com.cognixia.stagestream.services.UserService;
import com.cognixia.stagestream.security.JWTUtil;

import com.cognixia.stagestream.config.AppConstants;

import com.cognixia.stagestream.models.AuthenticationRequest;
import com.cognixia.stagestream.models.AuthenticationResponse;


@RestController
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JWTUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(AppConstants.REGISTER_URL)
    public AuthenticationResponse register(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        User registeredUser = userService.registerUser(user);
        
        String token = jwtUtil.generateToken(registeredUser.getUsername());
        
        return new AuthenticationResponse(token, registeredUser.getUserId());
    }

    @PostMapping(AppConstants.LOGIN_URL)
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String token = jwtUtil.generateToken(request.getUsername());

            User user = userService.getUserByUsername(request.getUsername());

            return new AuthenticationResponse(token, user.getUserId());
        } catch (AuthenticationException e) {
            throw e; 
        }
    }
}