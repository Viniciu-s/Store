package com.vinicius.product.controller;

import com.vinicius.product.domain.dto.BrandRequest;
import com.vinicius.product.domain.dto.BrandResponse;
import com.vinicius.product.service.impl.BrandServiceImpl;
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
@RequestMapping("/brand")
public class BrandController {

    private final BrandServiceImpl service;

    public BrandController(BrandServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EntityModel<BrandResponse>> createBrand(@RequestBody @Valid BrandRequest brandRequest) {
        BrandResponse brand = service.createBrand(brandRequest);
        EntityModel<BrandResponse> resource = EntityModel.of(brand);
        resource.add(Link.of("/brand/" + brand.id()).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @GetMapping
    public List<EntityModel<BrandResponse>> listBrands() {
        List<BrandResponse> brands = service.listBrands();
        return brands.stream()
                .map(brand -> EntityModel.of(brand)
                        .add(Link.of("/brand/" + brand.id()).withSelfRel()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BrandResponse>> searchBrandForId(@PathVariable UUID id) {
        BrandResponse brand = service.searchBrandForId(id);
        EntityModel<BrandResponse> resource = EntityModel.of(brand);
        resource.add(Link.of("/brand/" + id).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<BrandResponse>> updateBrand(@PathVariable UUID id, @RequestBody @Valid BrandRequest brandRequest) {
        BrandResponse updatedBrand = service.updateBrand(id, brandRequest);
        EntityModel<BrandResponse> resource = EntityModel.of(updatedBrand);
        resource.add(Link.of("/brand/" + id).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable UUID id) {
        boolean deletedBrand = service.deleteBrand(id);
        return deletedBrand ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
