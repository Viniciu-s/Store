package com.vinicius.product.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vinicius.product.domain.dto.ProductResponse;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private String color;
    @Column(nullable = false)
    private String size;
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @JsonBackReference
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    public Product() {
    }

    public Product(UUID id, String name, BigDecimal price, String image, String color, String size, String description,
                   Brand brand, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.color = color;
        this.size = size;
        this.description = description;
        this.brand = brand;
        this.category = category;
    }

    public Product(ProductResponse productResponse) {
        this.id = productResponse.id();
        this.name = productResponse.name();
        this.price = productResponse.price();
        this.image = productResponse.image();
        this.color = productResponse.color();
        this.size = productResponse.size();
        this.description = productResponse.description();
        this.brand = new Brand(productResponse.brand());
        this.category = new Category(productResponse.category());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price)
                && Objects.equals(image, product.image) && Objects.equals(color, product.color)
                && Objects.equals(size, product.size) && Objects.equals(description, product.description) && Objects.equals(brand, product.brand) &&
                Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, image, color, size, description, brand, category);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", description='" + description + '\'' +
                ", brand=" + brand +
                ", category=" + category +
                '}';
    }
}