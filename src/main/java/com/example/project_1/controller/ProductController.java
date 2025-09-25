package com.example.project_1.controller;

import com.example.project_1.model.Product;
import com.example.project_1.service.ProductService;
import com.example.project_1.service.WRSService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ProductController {
	



    @Autowired
    private ProductService service;
    
    @Autowired
    private WRSService wrsService;

    @Autowired
    private ObjectMapper objectMapper;
    
    

    @RequestMapping("/")
    public String greet() {
        return "Hello World";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct() {
        return new ResponseEntity<>(service.getAllProduct(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        return new ResponseEntity<>(service.getProductId(id), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart("product") String productJson,
                                        @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            Product product = objectMapper.readValue(productJson, Product.class);
            Product saved = service.addProduct(product, imageFile);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
        Product product = service.getProductId(productId);
        byte[] imageFile = product.getImageDate();
        String imageType = product.getImageType();

        if (imageFile == null || imageType == null || imageType.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageType))
                .body(imageFile);  
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id,
                                           @RequestPart("product") String productJson,
                                           @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            Product product = objectMapper.readValue(productJson, Product.class);
            Product updated = service.updateProduct(id, product, imageFile);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Update failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product product = service.getProductId(id);
        if (product != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
        }
    }
    
    
    
    @GetMapping("/wrs/parts")
    public ResponseEntity<String> getWTPartsFromWindchill() {
        try {
            String partsJson = wrsService.getPartsData();
            return ResponseEntity.ok(partsJson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to fetch parts from Windchill: " + e.getMessage());
        }
    }
    
    
    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword)
    {
    	List<Product> products =service.searchProducts(keyword);
    	
    	
		return new ResponseEntity<>(products,HttpStatus.FOUND);
    	
    }
    
    
    @GetMapping("/product/generateimage")
    public String genrateImage(@RequestParam String text)
    {
		return text;
    	
    }
    
    
    
    
    
}
