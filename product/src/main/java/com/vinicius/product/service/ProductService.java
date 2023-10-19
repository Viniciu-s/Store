package com.vinicius.product.service;

import com.vinicius.product.domain.dto.ProductResponse;
import com.vinicius.product.domain.entity.Product;
import com.vinicius.product.exceptions.ProductNotFoundException;
import com.vinicius.product.mapper.ProductMapper;
import com.vinicius.product.repository.ProductRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper productMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    ProductService(ProductRepository repository, ProductMapper productMapper, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.productMapper = productMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public ProductResponse createProduct(ProductResponse productResponse) {
        Product product = productMapper.productResponseToProduct(productResponse);
        repository.save(product);
        rabbitTemplate.convertAndSend("product.ex", "", product);
        return productMapper.productToProductResponse(product);
    }

    public List<ProductResponse> listProducts() {
        List<Product> products = repository.findAll();
        return products.stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse searchProductForId(UUID id) {
        Optional<Product> productOptional = repository.findById(id);
        return productOptional.map(productMapper::productToProductResponse)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));
    }

    public ProductResponse updateProduct(UUID id, ProductResponse dto) {
        Product product = productMapper.productResponseToProduct(dto);
        product.setId(id);
        product = repository.save(product);
        return productMapper.productToProductResponse(product);
    }

    public boolean deleteProduct(UUID id) {
        Optional<Product> productOptional = repository.findById(id);
        if (productOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new ProductNotFoundException("Produto não encontrado com ID: " + id);
        }
    }

    public List<ProductResponse> listProductsByCategory(String category) {
        List<Product> products = repository.findByCategory(category);
        return products.stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> listProductsByName(String name) {
        List<Product> products = repository.findByName(name);
        return products.stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList());
    }
}