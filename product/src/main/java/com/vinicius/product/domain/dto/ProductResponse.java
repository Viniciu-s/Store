package com.vinicius.product.domain.dto;

import com.vinicius.product.domain.enums.ProductStatus;
import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        BigDecimal price,
        String image,
        String color,
        String size,
        String description,
        ProductStatus status,
        BrandResponse brand,
        CategoryResponse category
) {
}