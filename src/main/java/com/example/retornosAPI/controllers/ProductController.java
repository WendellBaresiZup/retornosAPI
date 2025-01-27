package com.example.retornosAPI.controllers;

import com.example.retornosAPI.models.Product;
import com.example.retornosAPI.models.ProductEntity;
import com.example.retornosAPI.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody Product product) {
        ResponseEntity<Map<String,Object>> productCreated = service.createProduct(product);
        return ResponseEntity.status(productCreated.getStatusCode()).body(productCreated.getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        return service.deleteProduct(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long id, @RequestBody Product updatedProduct){
        try {
            Product updated = service.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductEntity>> getProductByName(@RequestParam String name){
        try {
            List<ProductEntity> products = service.getProductsByName(name);
            if (products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }
}