package com.vinicius.product.service.impl;

import com.vinicius.product.domain.dto.BrandRequest;
import com.vinicius.product.domain.dto.BrandResponse;
import com.vinicius.product.domain.entity.Brand;
import com.vinicius.product.exceptions.BrandNotFoundException;
import com.vinicius.product.mapper.ProductMapper;
import com.vinicius.product.repository.BrandRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {

    @Mock
    private BrandRepository repository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Nested
    class CreateBrand {

        @Test
        @DisplayName("Deve criar uma marca")
        void shouldCreateBrand() {
            //Arrange
            BrandRequest brandRequest = new BrandRequest(
                    UUID.randomUUID(),
                    "Marca"
            );
            Brand brand = new Brand();
            BrandResponse brandResponse = new BrandResponse(
                    brandRequest.id(),
                    brandRequest.brandName()
            );

            //Mocks
            doReturn(brand).when(productMapper).brandRequestToBrand(brandRequest);
            doReturn(brand).when(repository).save(brand);
            doReturn(brandResponse).when(productMapper).brandToBrandResponse(brand);
            //Act
            BrandResponse result = brandService.createBrand(brandRequest);
            //Assert
            assertNotNull(result);
            assertEquals(brandRequest.id(), result.id());
            assertEquals(brandRequest.brandName(), result.brandName());

            //Verificar se os métodos foram chamados
            verify(repository).save(brand);
            //Verificar se o método convertAndSend foi chamado
            verify(rabbitTemplate).convertAndSend("product.ex", "", brand);
        }

        @Test
        @DisplayName("Deve lançar uma exceção quando a marca for nula")
        void shouldThrowNullPointerExceptionWhenBrandIsNull() {
            //Arrange
            BrandRequest brandRequest = new BrandRequest(
                    UUID.randomUUID(),
                    "Marca"
            );

            //Mocks
            doReturn(null).when(productMapper).brandRequestToBrand(brandRequest);
            //Act and Assert
            NullPointerException exception = assertThrows(NullPointerException.class, () -> brandService.createBrand(brandRequest));
            assertEquals("Marca não pode ser nula", exception.getMessage());
        }
    }

    @Nested
    class ListBrands {

        @Test
        @DisplayName("Deve listar todas as marcas")
        void shouldListAllBrands() {
            //Arrange
            Brand brand = new Brand();
            BrandResponse brandResponse = new BrandResponse(
                    UUID.randomUUID(),
                    "Marca"
            );

            //Mocks
            when(repository.findAll()).thenReturn(List.of(brand));
            when(productMapper.brandToBrandResponse(brand)).thenReturn(brandResponse);
            //Act
            List<BrandResponse> result = brandService.listBrands();
            //Assert
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            assertEquals(brandResponse, result.get(0));
        }

        @Test
        @DisplayName("Deve lançar uma exceção quando a lista de marcas estiver vazia")
        void shouldThrowBrandNotFoundExceptionWhenListIsEmpty() {
            //Arrange
            when(repository.findAll()).thenReturn(List.of());
            //Act and Assert
            BrandNotFoundException exception = assertThrows(BrandNotFoundException.class, () -> brandService.listBrands());
            assertEquals("Marca não encontrada", exception.getMessage());
        }
    }

    @Nested
    class SearchBrandForId {

        @Test
        @DisplayName("Deve buscar uma marca por id")
        void shouldSearchBrandForId() {
            //Arrange
            UUID id = UUID.randomUUID();
            Brand brand = new Brand();
            BrandResponse brandResponse = new BrandResponse(
                    id,
                    "Marca"
            );

            //Mocks
            when(repository.findById(id)).thenReturn(java.util.Optional.of(brand));
            when(productMapper.brandToBrandResponse(brand)).thenReturn(brandResponse);
            //Act
            BrandResponse result = brandService.searchBrandForId(id);
            //Assert
            assertNotNull(result);
            assertEquals(brandResponse, result);
        }

        @Test
        @DisplayName("Deve lançar uma exceção quando a marca não for encontrada")
        void shouldThrowBrandNotFoundExceptionWhenBrandNotFound() {
            //Arrange
            UUID id = UUID.randomUUID();
            //Mocks
            when(repository.findById(id)).thenReturn(java.util.Optional.empty());
            //Act and Assert
            assertThrows(BrandNotFoundException.class, () -> brandService.searchBrandForId(id));
        }
    }

    @Nested
    class ListBrandsByName {

        @Test
        @DisplayName("Deve listar marcas por nome")
        void shouldListBrandsByName() {
            //Arrange
            String brandName = "Marca";
            Brand brand = new Brand();
            BrandResponse brandResponse = new BrandResponse(
                    UUID.randomUUID(),
                    brandName
            );

            //Mocks
            when(repository.findByBrandName(brandName)).thenReturn(List.of(brand));
            when(productMapper.brandToBrandResponse(brand)).thenReturn(brandResponse);
            //Act
            List<BrandResponse> result = brandService.listBrandsByName(brandName);
            //Assert
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            assertEquals(brandResponse, result.get(0));
        }

        @Test
        @DisplayName("Deve lançar uma exceção quando a lista de marcas estiver vazia")
        void shouldThrowBrandNotFoundExceptionWhenListIsEmpty() {
            //Arrange
            String brandName = "Marca";
            //Mocks
            when(repository.findByBrandName(brandName)).thenReturn(List.of());
            //Act and Assert
            BrandNotFoundException exception = assertThrows(BrandNotFoundException.class, () -> brandService.listBrandsByName(brandName));
            assertEquals("Marca não encontrada com o nome: " + brandName, exception.getMessage());
        }
    }

    @Nested
    class UpdateBrand {

        @Test
        @DisplayName("Deve atualizar uma marca")
        void shouldUpdateBrand() {
            //Arrange
            UUID id = UUID.randomUUID();
            BrandRequest brandRequest = new BrandRequest(
                    id,
                    "Marca"
            );
            Brand brand = new Brand();
            BrandResponse brandResponse = new BrandResponse(
                    id,
                    "Marca"
            );

            //Mocks
            when(productMapper.brandRequestToBrand(brandRequest)).thenReturn(brand);
            when(repository.save(brand)).thenReturn(brand);
            when(productMapper.brandToBrandResponse(brand)).thenReturn(brandResponse);
            //Act
            BrandResponse result = brandService.updateBrand(id, brandRequest);
            //Assert
            assertNotNull(result);
            assertEquals(brandResponse, result);
        }

        @Test
        @DisplayName("Deve lançar uma exceção quando a marca não for encontrada")
        void shouldThrowBrandNotFoundExceptionWhenBrandNotFound() {
            //Arrange
            UUID id = UUID.randomUUID();
            BrandRequest brandRequest = new BrandRequest(
                    id,
                    "Marca"
            );
            //Mocks
            doReturn(null).when(productMapper).brandRequestToBrand(brandRequest);

            //Act and Assert
            assertThrows(NullPointerException.class, () -> brandService.updateBrand(id, brandRequest));
        }
    }

    @Nested
    class DeleteBrand {

        @Test
        @DisplayName("Deve deletar uma marca")
        void shouldDeleteBrand() {
            //Arrange
            UUID id = UUID.randomUUID();
            Brand brand = new Brand();
            //Mocks
            when(repository.findById(id)).thenReturn(java.util.Optional.of(brand));
            //Act
            boolean result = brandService.deleteBrand(id);
            //Assert
            assertTrue(result);
            verify(repository).deleteById(id);
        }

        @Test
        @DisplayName("Deve lançar uma exceção quando a marca não for encontrada")
        void shouldThrowBrandNotFoundExceptionWhenBrandNotFound() {
            //Arrange
            UUID id = UUID.randomUUID();
            //Mocks
            when(repository.findById(id)).thenReturn(java.util.Optional.empty());
            //Act and Assert
            assertThrows(BrandNotFoundException.class, () -> brandService.deleteBrand(id));
        }
    }

}