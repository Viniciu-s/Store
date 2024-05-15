package com.vinicius.product.mapper;

import com.vinicius.product.domain.dto.*;
import com.vinicius.product.domain.entity.Brand;
import com.vinicius.product.domain.entity.Category;
import com.vinicius.product.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product productRequestToProduct(ProductRequest productRequest);

    ProductResponse productToProductResponse(Product product);

    Brand brandRequestToBrand(BrandRequest brandRequest);

    BrandResponse brandToBrandResponse(Brand brand);

    Category categoryRequestToCategory(CategoryRequest categoryRequest);

    CategoryResponse categoryToCategoryResponse(Category category);
}