package com.example.project_1.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
	


	public Product getProductId(int id) {
	    return repo.findById(id)
	    		.orElseThrow(() -> new RuntimeException("Product not found"));

	}

	public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
		// TODO Auto-generated method stub
		
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageDate(imageFile.getBytes());
		
		return repo.save(product);
	}

	public Product updateProduct(int id, Product updatedProduct, MultipartFile imageFile) throws IOException {
	    Product existingProduct = repo.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

	    // update fields
	    existingProduct.setName(updatedProduct.getName());
	    existingProduct.setBrand(updatedProduct.getBrand());
	    existingProduct.setCategory(updatedProduct.getCategory());
	    existingProduct.setDescription(updatedProduct.getDescription());
	    existingProduct.setExpiryDate(updatedProduct.getExpiryDate());
	    existingProduct.setListedDate(updatedProduct.getListedDate());
	    existingProduct.setPrice(updatedProduct.getPrice());
	    existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
	    existingProduct.setAvailable(updatedProduct.isAvailable());

	    if (imageFile != null && !imageFile.isEmpty()) {
	        existingProduct.setImageDate(imageFile.getBytes());
	        existingProduct.setImageName(imageFile.getOriginalFilename());
	        existingProduct.setImageType(imageFile.getContentType());
	    }

	    return repo.save(existingProduct);
	}


	public void deleteProduct(int id) {
		repo.deleteById(id);
	}



	public List<Product> searchProducts(String keyword) {
		
		return repo.searchProducts(keyword);
	}

	

}
