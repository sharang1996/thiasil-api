package com.thiasil.thiasil_api.controller;

import com.thiasil.thiasil_api.model.Product;
import com.thiasil.thiasil_api.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam(name = "category") String category){
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }



}
