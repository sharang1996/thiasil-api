package com.thiasil.thiasil_api.repository;

import com.thiasil.thiasil_api.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
  List<Product> findByCategoryIgnoreCaseOrderByPriceAsc(String category);

  Optional<Product> findByCatalogueNumber(String catalogueNumber);

  Iterable<Product> findAll();

  Product save(Product product);

  List<Product> deleteByCatalogueNumber(String catalogueNumber);
}
