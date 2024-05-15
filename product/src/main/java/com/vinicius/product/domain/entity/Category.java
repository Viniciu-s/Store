package com.vinicius.product.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vinicius.product.domain.dto.CategoryResponse;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Product> products;

    public Category() {
    }

    public Category(UUID id, String categoryName, List<Product> products) {
        this.id = id;
        this.categoryName = categoryName;
        this.products = products;
    }

    public Category(CategoryResponse categoryResponse) {
        this.id = categoryResponse.id();
        this.categoryName = categoryResponse.categoryName();
        this.products = categoryResponse.products();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(categoryName, category.categoryName) && Objects.equals(products, category.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName, products);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", products=" + products +
                '}';
    }
}
