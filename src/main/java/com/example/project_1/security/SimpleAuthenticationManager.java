package com.example.project_1.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class SimpleAuthenticationManager implements AuthenticationManager {

    // In-memory user store (username -> password)
    private Map<String, String> users = new HashMap<>();

    public SimpleAuthenticationManager() {
        users.put("omkar", "12345"); // Example user
        users.put("admin", "admin123"); // Admin user
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            // Successful authentication
            return new UsernamePasswordAuthenticationToken(username, null, null); // roles can be added
        } else {
        	 throw new BadCredentialsException("Invalid username or password");
        }
    }


}
