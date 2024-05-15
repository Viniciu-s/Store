package com.vinicius.product.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vinicius.product.domain.dto.BrandResponse;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_brand")
public class Brand implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String brandName;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Product> products;

    public Brand() {
    }

    public Brand(BrandResponse brandResponse) {
        this.id = brandResponse.id();
        this.brandName = brandResponse.brandName();
        this.products = brandResponse.products();
    }

    public Brand(UUID id, String brandName, List<Product> products) {
        this.id = id;
        this.brandName = brandName;
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public String getBrandName() {
        return brandName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(id, brand.id) && Objects.equals(brandName, brand.brandName) && Objects.equals(products, brand.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brandName, products);
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", products=" + products +
                '}';
    }
}