package com.thiasil.thiasil_api.service;

import com.thiasil.thiasil_api.repository.ProductRepository;
import com.thiasil.thiasil_api.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCaseOrderByPriceAsc(category);
    }
}
