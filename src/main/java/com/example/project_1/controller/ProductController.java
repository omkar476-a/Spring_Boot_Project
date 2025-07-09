package com.example.project_1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_1.model.Product;
import com.example.project_1.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:5173") // Allow React frontend // to avoid cross connection 

@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService service;
	

	@RequestMapping("/")
	public String greet()
	{
		return "Hello World";
	}
	
	
	//if you want to send response also with product use 
	@GetMapping("/products")
	public  ResponseEntity< List<Product> >getAllProduct()
	{
	return new ResponseEntity<>(service.getAllProduct(),HttpStatus.OK);
	
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id)
	{
	return new ResponseEntity<>(service.getProductId(id),HttpStatus.OK);
	
	}
	
	
	
	
}
