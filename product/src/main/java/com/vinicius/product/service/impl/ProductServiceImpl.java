package com.vinicius.product.service.impl;

import com.vinicius.product.domain.dto.ProductRequest;
import com.vinicius.product.domain.dto.ProductResponse;
import com.vinicius.product.domain.entity.Product;
import com.vinicius.product.domain.enums.ProductStatus;
import com.vinicius.product.exceptions.ProductNotFoundException;
import com.vinicius.product.mapper.ProductMapper;
import com.vinicius.product.repository.ProductRepository;
import com.vinicius.product.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository repository;
    private final ProductMapper productMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    ProductServiceImpl(ProductRepository repository, ProductMapper productMapper, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.productMapper = productMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductResponse createProduct(ProductRequest productRequest) {
        logger.info("Criando produtos");
        Product product = productMapper.productRequestToProduct(productRequest);
        if (product == null) {throw new NullPointerException("Produto não pode ser nulo");}
        repository.save(product);
        rabbitTemplate.convertAndSend("product.ex", "", product);
        return productMapper.productToProductResponse(product);
    }

    public List<ProductResponse> listProducts() {
        logger.info("Listando produtos");
        List<Product> products = repository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Produto não encontrado");
        }
        return products.stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse searchProductForId(UUID id) {
        logger.info("Buscando produtos");
        Optional<Product> productOptional = repository.findById(id);
        return productOptional.map(productMapper::productToProductResponse)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado com o id: " + id));
    }

    public ProductResponse updateProduct(UUID id, ProductRequest productRequest) {
        logger.info("Atualizando produtos");
        Product product = productMapper.productRequestToProduct(productRequest);
        product.setId(id);
        product = repository.save(product);
        return productMapper.productToProductResponse(product);
    }

    public boolean deleteProduct(UUID id) {
        logger.info("Deletando produtos");
        Optional<Product> productOptional = repository.findById(id);
        if (productOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new ProductNotFoundException("Produto não encontrado com o id: " + id);
        }
    }

    public List<ProductResponse> listProductsByName(String name) {
        logger.info("Listando produtos por nome");
        List<Product> products = repository.findByName(name);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Nenhum produto encontrado com o nome: " + name);
        }
        return products.stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList());
    }


    public List<ProductResponse> listProductsByColor(String color) {
        logger.info("Listando produtos por cor");
        List<Product> products = repository.findByColor(color);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Nenhum produto encontrado com a cor: " + color);
        }
        return products.stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> listProductsBySize(String size) {
        logger.info("Listando produtos por tamanho");
        List<Product> products = repository.findBySize(size);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Nenhum produto encontrado com o tamanho: " + size);
        }
        return products.stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse reserveProduct(UUID id) {
        logger.info("Reservando produto com id: {}", id);

        Optional<Product> productOptional = repository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setStatus(ProductStatus.RESERVADO);
            repository.save(product);
            return productMapper.productToProductResponse(product);
        } else {
            throw new ProductNotFoundException("Produto não encontrado com o id: " + id);
        }
    }
}