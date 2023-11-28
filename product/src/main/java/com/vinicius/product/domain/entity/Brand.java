package com.vinicius.product.domain.entity;

import com.vinicius.product.domain.dto.BrandResponse;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String brandName;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;

    public Brand() {
    }

    public Brand(UUID id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    public Brand(BrandResponse brandResponse) {
        this.id = brandResponse.id();
        this.brandName = brandResponse.brandName();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}