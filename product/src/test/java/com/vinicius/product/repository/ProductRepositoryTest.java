package com.vinicius.product.repository;

import com.vinicius.product.domain.dto.BrandResponse;
import com.vinicius.product.domain.dto.ProductResponse;
import com.vinicius.product.domain.entity.Brand;
import com.vinicius.product.domain.entity.Product;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Autowired
    EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryTest.class);

    @Test
    @DisplayName("Sucesso ao consultar produto por nome")
    void findByNameCase2() {
        String name = "camiseta";

        List<Product> result = this.repository.findByName(name);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Sucesso ao consultar produto por categoria")
    void findByCategoryCase2() {
        String category = "camisetas";

        List<Product> result = this.repository.findByName(category);

        assertThat(result.isEmpty()).isTrue();
    }
}