package com.vinicius.product.service.impl;

import com.vinicius.product.domain.dto.BrandRequest;
import com.vinicius.product.domain.dto.BrandResponse;
import com.vinicius.product.domain.entity.Brand;
import com.vinicius.product.exceptions.BrandNotFoundException;
import com.vinicius.product.mapper.ProductMapper;
import com.vinicius.product.repository.BrandRepository;
import com.vinicius.product.service.IBrandService;
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
public class BrandServiceImpl implements IBrandService {

    private final BrandRepository repository;
    private final ProductMapper productMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BrandServiceImpl(BrandRepository repository, ProductMapper productMapper, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.productMapper = productMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public BrandResponse createBrand(BrandRequest brandRequest) {
        logger.info("Criando marcas");
        Brand brand = productMapper.brandRequestToBrand(brandRequest);
        if (brand == null) {
            throw new NullPointerException("Marca não pode ser nula");
        }
        repository.save(brand);
        rabbitTemplate.convertAndSend("product.ex", "", brand);
        return productMapper.brandToBrandResponse(brand);
    }

    public List<BrandResponse> listBrands() {
        logger.debug("Listando marcas");
        List<Brand> brands = repository.findAll();
        if (brands.isEmpty()) {
            throw new BrandNotFoundException("Marca não encontrada");
        }
        return brands.stream()
                .map(productMapper::brandToBrandResponse)
                .collect(Collectors.toList());
    }

    public BrandResponse searchBrandForId(UUID id) {
        logger.info("Listando marcas por id");
        Optional<Brand> brandOptional = repository.findById(id);
        return brandOptional.map(productMapper::brandToBrandResponse)
                .orElseThrow(() -> new BrandNotFoundException("Marca não encontrada com o id: " + id));
    }

    public List<BrandResponse> listBrandsByName(String brandName) {
        logger.info("Listando Marcas por nome");
        List<Brand> brands = repository.findByBrandName(brandName);
        if (brands.isEmpty()) {
            throw new BrandNotFoundException("Marca não encontrada com o nome: " + brandName);
        }
        return brands.stream()
                .map(productMapper::brandToBrandResponse)
                .collect(Collectors.toList());
    }

    public BrandResponse updateBrand(UUID id, BrandRequest brandRequest) {
        logger.info("Atualizando marcas");
        Brand brand = productMapper.brandRequestToBrand(brandRequest);
        brand.setId(id);
        brand = repository.save(brand);
        return productMapper.brandToBrandResponse(brand);
    }

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