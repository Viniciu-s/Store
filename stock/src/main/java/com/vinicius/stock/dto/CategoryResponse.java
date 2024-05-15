package com.vinicius.stock.dto;


import java.util.List;
import java.util.UUID;

public record CategoryResponse(UUID id, String categoryName, List<ProductResponse> products) {
}
