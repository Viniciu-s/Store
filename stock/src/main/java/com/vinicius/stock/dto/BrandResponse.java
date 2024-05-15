package com.vinicius.stock.dto;

import java.util.List;
import java.util.UUID;

public record BrandResponse(UUID id, String brandName, List<ProductResponse> products) {
}
