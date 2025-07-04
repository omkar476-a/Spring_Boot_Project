package com.example.project_1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project_1.model.Product;
import com.example.project_1.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo repo;
	public List<Product> getAllProduct() {
		System.out.println("in get all method");
		return repo.findAll();
		
	}

}
