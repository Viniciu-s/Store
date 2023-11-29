package com.vinicius.product.repository;

import com.vinicius.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategory(String category);

    List<Product> findByName(String name);

    List<Product> findByColor(String color);

    List<Product> findBySize(String size);
}
