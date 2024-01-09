package com.vinicius.product.repository;

import com.vinicius.product.domain.dto.BrandResponse;
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

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class BrandRepositoryTest {

    @Autowired
    BrandRepository repository;

    @Autowired
    EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(BrandRepositoryTest.class);

    @Test
    @DisplayName("Sucesso ao consultar marca por nome")
    void findByBrandNameCase1() {
        String brandName = "adidas";
        List<Product> adidasProducts = List.of(
                new Product()
        );
        BrandResponse brandResponse = new BrandResponse(UUID.randomUUID(), "adidas", adidasProducts);
        this.createBrand(brandResponse);

        List<Brand> result = this.repository.findByBrandName(brandName);

        logger.info("Result: " + result);
        assertThat(result.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Sucesso ao consultar marca por nome")
    void findByBrandNameCase2() {
        String brandName = "adidas";

        List<Brand> result = this.repository.findByBrandName(brandName);

        assertThat(result.isEmpty()).isTrue();
    }

    private void createBrand(BrandResponse brandResponse){
        Brand newBrand = new Brand(brandResponse);
        this.entityManager.merge(newBrand);
    }
}