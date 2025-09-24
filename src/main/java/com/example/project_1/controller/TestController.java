/*
 * package com.example.project_1.controller;
 * 
 * import org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.example.project_1.model.User; import
 * com.example.project_1.repo.UserRepo;
 * 
 * @RestController
 * 
 * @RequestMapping("/auth") public class TestController {
 * 
 * private final UserRepo userRepository; private final PasswordEncoder
 * passwordEncoder;
 * 
 * public TestController(UserRepo userRepository, PasswordEncoder
 * passwordEncoder) { this.userRepository = userRepository; this.passwordEncoder
 * = passwordEncoder; }
 * 
 * @PostMapping("/register") public String register(@RequestParam String
 * username, @RequestParam String password) { // check if user exists
 * if(userRepository.findByusername(username).isPresent()) { return
 * "User already exists!"; }
 * 
 * User user = new User(); user.setUsername(username);
 * user.setPassword(passwordEncoder.encode(password));
 * user.setRole("ROLE_USER");
 * 
 * userRepository.save(user); // ✅ saves in database
 * 
 * return "User registered successfully!"; }
 * 
 * 
 * 
 * // @PostMapping("/login") // public String login(@RequestParam String
 * username,@RequestParam String password) // { //
 * UsernamePasswordAuthenticationToken // return "Login Successful"; // // } }
 */