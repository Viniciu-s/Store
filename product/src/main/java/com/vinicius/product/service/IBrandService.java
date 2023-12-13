package com.vinicius.product.service;

import com.vinicius.product.domain.dto.BrandRequest;
import com.vinicius.product.domain.dto.BrandResponse;

import java.util.List;
import java.util.UUID;

public interface IBrandService{

    BrandResponse createBrand(BrandRequest brandRequest);
    List<BrandResponse> listBrands();
    BrandResponse searchBrandForId(UUID id);
    BrandResponse updateBrand(UUID id, BrandRequest brandRequest);
    boolean deleteBrand(UUID id);
}
