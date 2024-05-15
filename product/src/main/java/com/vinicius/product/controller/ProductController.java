package com.vinicius.product.controller;

import com.vinicius.product.domain.dto.ProductRequest;
import com.vinicius.product.domain.dto.ProductResponse;
import com.vinicius.product.service.impl.ProductServiceImpl;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductServiceImpl service;

    public ProductController(ProductServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EntityModel<ProductResponse>> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        ProductResponse product = service.createProduct(productRequest);
        EntityModel<ProductResponse> resource = EntityModel.of(product);
        resource.add(Link.of("/product/" + product.id()).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @GetMapping
    public List<EntityModel<ProductResponse>> listProducts() {
        List<ProductResponse> products = service.listProducts();
        return products.stream()
                .map(product -> EntityModel.of(product)
                        .add(Link.of("/product/" + product.id()).withSelfRel()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductResponse>> searchProductForId(@PathVariable UUID id) {
        ProductResponse product = service.searchProductForId(id);
        EntityModel<ProductResponse> resource = EntityModel.of(product);
        resource.add(Link.of("/product/" + id).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductResponse>> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductRequest productRequest) {
        ProductResponse updatedProduct = service.updateProduct(id, productRequest);
        EntityModel<ProductResponse> resource = EntityModel.of(updatedProduct);
        resource.add(Link.of("/product/" + id).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        boolean deletedProduct = service.deleteProduct(id);
        return deletedProduct ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/name/{name}")
    public List<ProductResponse> getProductsByName(@PathVariable String name) {
        return service.listProductsByName(name);
    }

    @GetMapping("/color/{color}")
    public List<ProductResponse> getProductsByColor(@PathVariable String color) {
        return service.listProductsByColor(color);
    }

    @GetMapping("/size/{size}")
    public List<ProductResponse> getProductsBySize(@PathVariable String size) {
        return service.listProductsBySize(size);
    }
}
