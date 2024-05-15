package com.vinicius.product.service;

import com.vinicius.product.domain.dto.CategoryRequest;
import com.vinicius.product.domain.dto.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface ICategoryServiceImpl {

    CategoryResponse createCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> listCategory();
    CategoryResponse searchCategoryForId(UUID id);
    List<CategoryResponse> listCategoryByName(String categoryName);
    CategoryResponse updateCategory(UUID id, CategoryRequest categoryRequest);
    boolean deleteCategory(UUID id);
}
