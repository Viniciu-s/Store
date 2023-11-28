package com.vinicius.product.repository;

import com.vinicius.product.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    List<Brand> findByBrandName(String brandName);
}
