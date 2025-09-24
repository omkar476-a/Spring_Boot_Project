package com.example.project_1.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project_1.model.User;

public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByusername(String username);
	
	
}
