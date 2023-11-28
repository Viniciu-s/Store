package com.vinicius.product.domain.dto;

import com.vinicius.product.domain.entity.Product;

import java.util.List;
import java.util.UUID;

public record BrandResponse(UUID id, String brandName, List<Product> products) {
}
