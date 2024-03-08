package com.vinicius.stock.dto;

import com.vinicius.product.domain.dto.BrandResponse;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(UUID id, String name, String category, BigDecimal price, String image, String color, String size, String description, BrandResponse brand) {
}
