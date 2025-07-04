package com.example.project_1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project_1.model.Product;
import com.example.project_1.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService service;
	

	@RequestMapping("/")
	public String greet()
	{
		return "Hello World";
	}
	
	@GetMapping("/products")
	public  List<Product> getAllProduct()
	{
	return service.getAllProduct();
	
	}
	
}
