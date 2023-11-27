package com.vinicius.product.domain.entity;

import com.vinicius.product.domain.dto.ProductResponse;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private String description;
    @ManyToOne
    private Brand brand;

    public Product(){}

    public Product(ProductResponse productResponse) {
        this.id = productResponse.id();
        this.name = productResponse.name();
        this.category = productResponse.category();
        this.price = productResponse.price();
        this.image = productResponse.image();
        this.description = productResponse.description();
    }

    public Product(UUID id, String name, String category, BigDecimal price, String image, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.image = image;
        this.description = description;
    }

    public UUID getId() {return id;}

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}