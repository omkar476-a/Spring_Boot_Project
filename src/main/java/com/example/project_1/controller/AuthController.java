package com.example.project_1.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.project_1.model.AuthRequest;

import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AuthRequest request) {
	    try {
	        String credentials = request.getUsername() + ":" + request.getPassword();
	        String base64Creds = Base64.getEncoder().encodeToString(credentials.getBytes());

	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", "Basic " + base64Creds);
	        headers.set("Accept", "application/json");

	        HttpEntity<String> entity = new HttpEntity<>(headers);

	        RestTemplate restTemplate = new RestTemplate();

	        String csrfUrl = "http://ptpl312.pluraltechnology.com:80/Windchill/servlet/odata/v5/PTC/GetCSRFToken()";
	        ResponseEntity<String> response = restTemplate.exchange(
	            csrfUrl,
	            HttpMethod.GET,
	            entity,
	            String.class
	        );

	        // You can parse the CSRF token from the body or headers based on Windchill config
	        String responseBody = response.getBody(); // or response.getHeaders().get("X-CSRF-TOKEN")
	        return ResponseEntity.ok("Login successful. Token: " + responseBody);

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
	    }
	}

}
