package com.vinicius.product.service.impl;

import com.vinicius.product.domain.dto.CategoryRequest;
import com.vinicius.product.domain.dto.CategoryResponse;
import com.vinicius.product.domain.entity.Category;
import com.vinicius.product.exceptions.CategoryNotFoundException;
import com.vinicius.product.mapper.ProductMapper;
import com.vinicius.product.repository.CategoryRepository;
import com.vinicius.product.service.ICategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryServiceImpl {

    private final CategoryRepository repository;
    private final ProductMapper productMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository, ProductMapper productMapper, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.productMapper = productMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        logger.info("Criando categorias");
        Category category = productMapper.categoryRequestToCategory(categoryRequest);
        repository.save(category);
        rabbitTemplate.convertAndSend("product.ex", "", category);
        return productMapper.categoryToCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> listCategory() {
        logger.debug("Listando categorias");
        List<Category> categories = repository.findAll();
        return categories.stream()
                .map(productMapper::categoryToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse searchCategoryForId(UUID id) {
        logger.info("Listando categorias por id");
        Optional<Category> categoryOptional = repository.findById(id);
        return categoryOptional.map(productMapper::categoryToCategoryResponse)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada com o id: " + id));
    }

    @Override
    public List<CategoryResponse> listCategoryByName(String categoryName) {
        logger.info("Listando categorias por nome");
        List<Category> categories = repository.findByCategoryName(categoryName);
        return categories.stream()
                .map(productMapper::categoryToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse updateCategory(UUID id, CategoryRequest categoryRequest) {
        logger.info("Atualizando categorias");
        Category category = productMapper.categoryRequestToCategory(categoryRequest);
        category.setId(id);
        category = repository.save(category);
        return productMapper.categoryToCategoryResponse(category);
    }

    @Override
    public boolean deleteCategory(UUID id) {
        logger.info("Deletando categorias");
        Optional<Category> categoryOptional = repository.findById(id);
        if (categoryOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new CategoryNotFoundException("Categoria não encontrada com o id: " + id);
        }
    }
}
