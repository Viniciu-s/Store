package com.vinicius.product.service;

import com.vinicius.product.domain.dto.BrandRequest;
import com.vinicius.product.domain.dto.BrandResponse;
import com.vinicius.product.domain.entity.Brand;
import com.vinicius.product.exceptions.BrandNotFoundException;
import com.vinicius.product.mapper.ProductMapper;
import com.vinicius.product.repository.BrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository repository;
    private final ProductMapper productMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BrandService(BrandRepository repository, ProductMapper productMapper, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.productMapper = productMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Cacheable(value = "createBrand")
    public BrandResponse createBrand(BrandRequest brandRequest) {
        logger.info("Criando marcas");
        Brand brand = productMapper.brandRequestToBrand(brandRequest);
        repository.save(brand);
        rabbitTemplate.convertAndSend("product.ex", "", brand);
        return productMapper.brandToBrandResponse(brand);
    }

    @Cacheable(value = "ListBrands")
    public List<BrandResponse> listBrands() {
        logger.debug("Listando marcas");
        List<Brand> brands = repository.findAll();
        return brands.stream()
                .map(productMapper::brandToBrandResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "searchBrandForId")
    public BrandResponse searchBrandForId(UUID id) {
        logger.info("Listando marcas por id");
        Optional<Brand> brandOptional = repository.findById(id);
        return brandOptional.map(productMapper::brandToBrandResponse)
                .orElseThrow(() -> new BrandNotFoundException("Marca não encontrada com o id: " + id));
    }

    @Cacheable(value = "updateBrand")
    public BrandResponse updateBrand(UUID id, BrandRequest brandRequest) {
        logger.info("Atualizando marcas");
        Brand brand = productMapper.brandRequestToBrand(brandRequest);
        brand.setId(id);
        brand = repository.save(brand);
        return productMapper.brandToBrandResponse(brand);
    }

    @Cacheable(value = "deleteBrand")
    public boolean deleteBrand(UUID id) {
        logger.info("Deletando marcas");
        Optional<Brand> brandOptional = repository.findById(id);
        if (brandOptional.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            throw new BrandNotFoundException("Marca não encontrada com o id: " + id);
        }
    }
}