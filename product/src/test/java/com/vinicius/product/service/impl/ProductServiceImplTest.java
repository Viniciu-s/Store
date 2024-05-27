package com.vinicius.product.service.impl;

import com.vinicius.product.domain.dto.ProductRequest;
import com.vinicius.product.domain.dto.ProductResponse;
import com.vinicius.product.domain.entity.Product;
import com.vinicius.product.domain.enums.ProductStatus;
import com.vinicius.product.exceptions.ProductNotFoundException;
import com.vinicius.product.mapper.ProductMapper;
import com.vinicius.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ProductServiceImpl productService;

    @Nested
    class CreateProductTest {

        @Test
        @DisplayName("Deve criar um produto com sucesso")
        void shouldCreateProduct() {
            // Arrange
            ProductRequest productRequest = new ProductRequest(
                    UUID.randomUUID(),
                    "Product",
                    BigDecimal.TEN,
                    "image",
                    "color",
                    "size",
                    "description",
                    ProductStatus.DISPONIVEL,
                    null,
                    null);

            Product product = new Product(); // objeto Product simulando um mapeamento correto
            ProductResponse productResponse = new ProductResponse(
                    productRequest.id(),
                    productRequest.name(),
                    productRequest.price(),
                    productRequest.image(),
                    productRequest.color(),
                    productRequest.size(),
                    productRequest.description(),
                    productRequest.status(),
                    null,
                    null);

            // Configurar mocks
            doReturn(product).when(productMapper).productRequestToProduct(productRequest);
            doReturn(product).when(repository).save(any(Product.class));
            doReturn(productResponse).when(productMapper).productToProductResponse(product);

            // Act
            ProductResponse result = productService.createProduct(productRequest);

            // Assert
            assertNotNull(result);
            assertEquals(productRequest.id(), result.id());
            assertEquals(productRequest.name(), result.name());
            assertEquals(productRequest.price(), result.price());
            assertEquals(productRequest.image(), result.image());
            assertEquals(productRequest.color(), result.color());
            assertEquals(productRequest.size(), result.size());
            assertEquals(productRequest.description(), result.description());

            // Verificar se o método save foi chamado
            verify(repository).save(product);
            // Verificar se o método convertAndSend foi chamado
            verify(rabbitTemplate).convertAndSend("product.ex", "", product);
        }

        @Test
        @DisplayName("Deve lançar uma excessão quando o prdocuto é nulo")
        void shouldThrowExceptionWhenProductIsNull() {
            // Arrange
            ProductRequest productRequest = new ProductRequest(
                    UUID.randomUUID(),
                    "Product",
                    BigDecimal.TEN,
                    "image",
                    "color",
                    "size",
                    "description",
                    ProductStatus.DISPONIVEL,
                    null,
                    null);

            //mocks
            doReturn(null).when(productMapper).productRequestToProduct(productRequest);

            // Act and Assert
            NullPointerException exception = assertThrows(NullPointerException.class, () -> productService.createProduct(productRequest));
            assertEquals("Produto não pode ser nulo", exception.getMessage());
        }
    }

    @Nested
    class ListProductsTest {

        @Test
        @DisplayName("Deve listar produtos com sucesso")
        void shouldListProducts() {
            // Arrange
            Product product = new Product(); //objeto Product simulando um mapeamento correto
            ProductResponse productResponse = new ProductResponse(
                    UUID.randomUUID(),
                    "Product",
                    BigDecimal.TEN,
                    "image",
                    "color",
                    "size",
                    "description",
                    ProductStatus.DISPONIVEL,
                    null,
                    null);

            // Configurar mocks
            when(repository.findAll()).thenReturn(List.of(product));
            when(productMapper.productToProductResponse(product)).thenReturn(productResponse);

            // Act
            List<ProductResponse> result = productService.listProducts();

            // Assert
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            assertEquals(productResponse, result.get(0));
        }

        @Test
        @DisplayName("Deve lançar uma excessão quando a lista está vazia")
        void shouldThrowExceptionWhenListIsEmpty() {
            // Arrange
            when(repository.findAll()).thenReturn(List.of());

            // Act and Assert
            ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> productService.listProducts());
            assertEquals("Produto não encontrado", exception.getMessage());
        }
    }

    @Nested
    class SearchProductForIdTest {

        @Test
        @DisplayName("Deve buscar produto por id com sucesso")
        void shouldSearchProductForId() {
            // Arrange
            UUID id = UUID.randomUUID();
            Product product = new Product(); //objeto Product simulando um mapeamento correto
            ProductResponse productResponse = new ProductResponse(
                    id,
                    "Product",
                    BigDecimal.TEN,
                    "image",
                    "color",
                    "size",
                    "description",
                    ProductStatus.DISPONIVEL,
                    null,
                    null);

            //mocks
            when(repository.findById(id)).thenReturn(java.util.Optional.of(product));
            when(productMapper.productToProductResponse(product)).thenReturn(productResponse);

            // Act
            ProductResponse result = productService.searchProductForId(id);

            // Assert
            assertNotNull(result);
            assertEquals(productResponse, result);
        }

        @Test
        @DisplayName("Deve lançar uma excessão quando o produto não é encontrado")
        void shouldThrowExceptionWhenProductIsNotFound() {
            // Arrange
            UUID id = UUID.randomUUID();

            // Configurar mocks
            when(repository.findById(id)).thenReturn(Optional.empty());

            // Act and Assert
            assertThrows(ProductNotFoundException.class, () -> productService.searchProductForId(id));
        }
    }

    @Nested
    class UpdateProductTest {

        @Test
        @DisplayName("Deve atualizar um produto com sucesso")
        void shouldUpdateProduct() {
            // Arrange
            UUID id = UUID.randomUUID();
            ProductRequest productRequest = new ProductRequest(
                    id,
                    "Product",
                    BigDecimal.TEN,
                    "image",
                    "color",
                    "size",
                    "description",
                    ProductStatus.DISPONIVEL,
                    null,
                    null);

            Product product = new Product(); //objeto Product simulando um mapeamento correto
            ProductResponse productResponse = new ProductResponse(
                    id,
                    "Product",
                    BigDecimal.TEN,
                    "image",
                    "color",
                    "size",
                    "description",
                    ProductStatus.DISPONIVEL,
                    null,
                    null);

            // mocks
            doReturn(product).when(productMapper).productRequestToProduct(productRequest);
            doReturn(product).when(repository).save(product);
            doReturn(productResponse).when(productMapper).productToProductResponse(product);

            // Act
            ProductResponse result = productService.updateProduct(id, productRequest);

            // Assert
            assertNotNull(result);
            assertEquals(productResponse, result);
        }

        @Test
        @DisplayName("Deve lançar uma excessão quando o produto não é encontrado")
        void shouldThrowExceptionWhenProductIsNotFound() {
            // Arrange
            UUID id = UUID.randomUUID();
            ProductRequest productRequest = new ProductRequest(
                    id,
                    "Product",
                    BigDecimal.TEN,
                    "image",
                    "color",
                    "size",
                    "description",
                    ProductStatus.DISPONIVEL,
                    null,
                    null);

            //Mocks
            doReturn(null).when(productMapper).productRequestToProduct(productRequest);

            // Act and Assert
            assertThrows(NullPointerException.class, () -> productService.updateProduct(id, productRequest));
        }
    }

    @Nested
    class DeleteProductTest {

        @Test
        @DisplayName("Deve deletar um produto com sucesso")
        void shouldDeleteProduct() {
            // Arrange
            UUID id = UUID.randomUUID();
            Product product = new Product(); //Objeto Product simulando um mapeamento correto

            //Mocks
            when(repository.findById(id)).thenReturn(java.util.Optional.of(product));

            // Act
            boolean result = productService.deleteProduct(id);

            // Assert
            assertTrue(result);
            verify(repository).deleteById(id);
        }

        @Test
        @DisplayName("Deve lançar uma excessão quando o produto não é encontrado")
        void shouldThrowExceptionWhenProductIsNotFound() {
            // Arrange
            UUID id = UUID.randomUUID();

            // Configurar mocks
            when(repository.findById(id)).thenReturn(java.util.Optional.empty());

            // Act and Assert
            assertThrows(RuntimeException.class, () -> productService.deleteProduct(id));
        }
    }

    @Nested
    class ListProductsByNameTest {

        @Test
        @DisplayName("Deve listar produtos por nome com sucesso")
        void shouldListProductsByName() {
            // Arrange
            String name = "Product";
            Product product = new Product(); //objeto Product simulando um mapeamento correto
            ProductResponse productResponse = new ProductResponse(
                    UUID.randomUUID(),
                    name,
                    BigDecimal.TEN,
                    "image",
                    "color",
                    "size",
                    "description",
                    ProductStatus.DISPONIVEL,
                    null,
                    null);

            //Mocks
            when(repository.findByName(name)).thenReturn(List.of(product));
            when(productMapper.productToProductResponse(product)).thenReturn(productResponse);

            // Act
            List<ProductResponse> result = productService.listProductsByName(name);

            // Assert
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            assertEquals(productResponse, result.get(0));
        }

        @Test
        @DisplayName("Deve lançar uma excessão quando a lista está vazia")
        void shouldThrowExceptionWhenListIsEmpty() {
            // Arrange
            String name = "Product";
            when(repository.findByName(name)).thenReturn(List.of());

            // Act and Assert
            ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> productService.listProductsByName(name));
            assertEquals("Nenhum produto encontrado com o nome: " + name, exception.getMessage());
        }

    }

    @Nested
    class ListProductsByColorTest {

        @Test
        @DisplayName("Deve listar produtos por cor com sucesso")
        void shouldListProductsByColor() {
            // Arrange
            String color = "color";
            Product product = new Product(); //objeto Product simulando um mapeamento correto
            ProductResponse productResponse = new ProductResponse(
                    UUID.randomUUID(),
                    "Product",
                    BigDecimal.TEN,
                    "image",
                    color,
                    "size",
                    "description",
                    ProductStatus.DISPONIVEL,
                    null,
                    null);

            //mocks
            when(repository.findByColor(color)).thenReturn(List.of(product));
            when(productMapper.productToProductResponse(product)).thenReturn(productResponse);

            // Act
            List<ProductResponse> result = productService.listProductsByColor(color);

            // Assert
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            assertEquals(productResponse, result.get(0));
        }

        @Test
        @DisplayName("Deve lançar uma excessão quando a lista está vazia")
        void shouldThrowExceptionWhenListIsEmpty() {
            // Arrange
            String color = "color";
            when(repository.findByColor(color)).thenReturn(List.of());

            // Act and Assert
            ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> productService.listProductsByColor(color));
            assertEquals("Nenhum produto encontrado com a cor: " + color, exception.getMessage());
        }
    }

    @Nested
    class ListProductsBySizeTest {

        @Test
        @DisplayName("Deve listar produtos por tamanho com sucesso")
        void shouldListProductsBySize() {
            // Arrange
            String size = "size";
            Product product = new Product(); //objeto Product simulando um mapeamento correto
            ProductResponse productResponse = new ProductResponse(
                    UUID.randomUUID(),
                    "Product",
                    BigDecimal.TEN,
                    "image",
                    "color",
                    size,
                    "description",
                    ProductStatus.DISPONIVEL,
                    null,
                    null);

            //Mocks
            when(repository.findBySize(size)).thenReturn(List.of(product));
            when(productMapper.productToProductResponse(product)).thenReturn(productResponse);

            // Act
            List<ProductResponse> result = productService.listProductsBySize(size);

            // Assert
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            assertEquals(productResponse, result.get(0));
        }

        @Test
        @DisplayName("Deve lançar uma excessão quando a lista está vazia")
        void shouldThrowExceptionWhenListIsEmpty() {
            // Arrange
            String size = "GG";
            when(repository.findBySize(size)).thenReturn(List.of());

            // Act and Assert
            ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> productService.listProductsBySize(size));
            assertEquals("Nenhum produto encontrado com o tamanho: " + size, exception.getMessage());
        }
    }
}