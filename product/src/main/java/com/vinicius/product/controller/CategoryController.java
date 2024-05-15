package com.vinicius.product.controller;

import com.vinicius.product.domain.dto.CategoryRequest;
import com.vinicius.product.domain.dto.CategoryResponse;
import com.vinicius.product.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryServiceImpl service;

    public CategoryController(CategoryServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EntityModel<CategoryResponse>> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        CategoryResponse category = service.createCategory(categoryRequest);
        EntityModel<CategoryResponse> resource = EntityModel.of(category);
        resource.add(Link.of("/category/" + category.id()).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @GetMapping
    public List<EntityModel<CategoryResponse>> listCategories() {
        List<CategoryResponse> categories = service.listCategory();
        return categories.stream()
                .map(category -> EntityModel.of(category)
                        .add(Link.of("/category/" + category.id()).withSelfRel()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CategoryResponse>> searchCategoryForId(@PathVariable UUID id) {
        CategoryResponse category = service.searchCategoryForId(id);
        EntityModel<CategoryResponse> resource = EntityModel.of(category);
        resource.add(Link.of("/category/" + id).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/name/{categoryName}")
    public List<CategoryResponse> getCategoriesByName(@PathVariable String categoryName) {
        return service.listCategoryByName(categoryName);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CategoryResponse>> updateCategory(@PathVariable UUID id, @RequestBody @Valid CategoryRequest categoryRequest) {
        CategoryResponse updatedCategory = service.updateCategory(id, categoryRequest);
        EntityModel<CategoryResponse> resource = EntityModel.of(updatedCategory);
        resource.add(Link.of("/category/" + id).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
