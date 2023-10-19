package com.vinicius.product.mapper;

import com.vinicius.product.domain.dto.ProductResponse;
import com.vinicius.product.domain.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponse productToProductResponse(Product product);

    Product productResponseToProduct(ProductResponse productResponse);
}