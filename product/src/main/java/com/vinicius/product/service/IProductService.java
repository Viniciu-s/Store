package com.vinicius.product.service;

import com.vinicius.product.domain.dto.ProductRequest;
import com.vinicius.product.domain.dto.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface IProductService {

    ProductResponse createProduct(ProductRequest productRequest);
    List<ProductResponse> listProducts();
    ProductResponse searchProductForId(UUID id);
    ProductResponse updateProduct(UUID id, ProductRequest productRequest);
    boolean deleteProduct(UUID id);
    List<ProductResponse> listProductsByCategory(String category);
    List<ProductResponse> listProductsByName(String name);
    List<ProductResponse> listProductsByColor(String color);
    List<ProductResponse> listProductsBySize(String size);
}
