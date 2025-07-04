package com.example.project_1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project_1.model.Product;


@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{

}
