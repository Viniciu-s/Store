package com.vinicius.product.mapper;

import com.vinicius.product.domain.dto.BrandRequest;
import com.vinicius.product.domain.dto.BrandResponse;
import com.vinicius.product.domain.dto.ProductRequest;
import com.vinicius.product.domain.dto.ProductResponse;
import com.vinicius.product.domain.entity.Brand;
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
}