package com.vinicius.product.domain.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequest(UUID id, String name, String category, BigDecimal price, String image, String color, String size, String description) {
}
