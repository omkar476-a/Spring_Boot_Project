package com.example.project_1.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.example.project_1.model.AuthRequest;
import com.example.project_1.model.AuthResponse;
import com.example.project_1.security.JwtUtil;
import com.example.project_1.service.UserService;

@CrossOrigin(origins = "http://localhost:5173") // frontend URL
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {
                String token = jwtUtil.generateToken(request.getUsername());
                return new AuthResponse("Login successful!", token);
            } else {
                return new AuthResponse("Login failed!", null);
            }
        } catch (AuthenticationException e) {
            return new AuthResponse("Invalid username or password", null);
        }
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        try {
            // Save new user with default role
            userService.saveUser(request.getUsername(), request.getPassword(), "ROLE_USER");

            // Generate token after registration
            String token = jwtUtil.generateToken(request.getUsername());
            return new AuthResponse("Registration successful!", token);
        } catch (Exception e) {
            return new AuthResponse("Registration failed: " + e.getMessage(), null);
        }
    }
}
