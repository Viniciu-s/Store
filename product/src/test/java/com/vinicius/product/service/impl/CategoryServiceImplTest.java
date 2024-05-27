package com.vinicius.product.service.impl;

import com.vinicius.product.domain.dto.CategoryRequest;
import com.vinicius.product.domain.dto.CategoryResponse;
import com.vinicius.product.domain.entity.Category;
import com.vinicius.product.exceptions.CategoryNotFoundException;
import com.vinicius.product.mapper.ProductMapper;
import com.vinicius.product.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Nested
    class CreateCategory {

        @Test
        @DisplayName("Deve criar uma categoria")
        void shouldCreateCategory() {
            //Arrange
            CategoryRequest categoryRequest = new CategoryRequest(
                    UUID.randomUUID(),
                    "Categoria 1"
            );
            Category category = new Category();
            CategoryResponse categoryResponse = new CategoryResponse(
                    categoryRequest.id(),
                    categoryRequest.categoryName()
            );

            //Mocks
            doReturn(category).when(productMapper).categoryRequestToCategory(categoryRequest);
            doReturn(category).when(categoryRepository).save(category);
            doReturn(categoryResponse).when(productMapper).categoryToCategoryResponse(category);
            //Act
            CategoryResponse result = categoryService.createCategory(categoryRequest);
            //Assert
            assertNotNull(result);
            assertEquals(categoryRequest.id(), result.id());
            assertEquals(categoryRequest.categoryName(), result.categoryName());

            //Verificar se os métodos foram chamados
            verify(categoryRepository).save(category);
            //Verificar se o método convertAndSend foi chamado
            verify(rabbitTemplate).convertAndSend("product.ex", "", category);
        }

        @Test
        @DisplayName("Deve lançar uma exceção ao tentar criar uma categoria nula")
        void shouldThrowNullPointerExceptionWhenCreateCategoryIsNull() {
            //Arrange
            CategoryRequest categoryRequest = new CategoryRequest(
                    UUID.randomUUID(),
                    "Categoria 1"
            );

            //Mocks
            doReturn(null).when(productMapper).categoryRequestToCategory(categoryRequest);
            //Act and Assert
            NullPointerException exception = assertThrows(NullPointerException.class, () -> categoryService.createCategory(categoryRequest));
            assertEquals("Categoria não pode ser nula", exception.getMessage());
        }
    }

    @Nested
    class ListCategory {

        @Test
        @DisplayName("Deve listar todas as categorias")
        void shouldListAllCategories() {
            //Arrange
            Category category = new Category();
            CategoryResponse categoryResponse = new CategoryResponse(
                    UUID.randomUUID(),
                    "Categoria 1"
            );

            //Mocks
            when(categoryRepository.findAll()).thenReturn(List.of(category));
            when(productMapper.categoryToCategoryResponse(category)).thenReturn(categoryResponse);
            //Act
            List<CategoryResponse> result = categoryService.listCategory();
            //Assert
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            assertEquals(categoryResponse, result.get(0));
        }

        @Test
        @DisplayName("Deve lançar uma exceção ao tentar listar todas as categorias e não encontrar nenhuma")
        void shouldThrowExceptionWhenListIsEmpty() {
            // Arrange
            when(categoryRepository.findAll()).thenReturn(List.of());

            // Act and Assert
            CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> categoryService.listCategory());
            assertEquals("Categoria não encontrada", exception.getMessage());
        }
    }

    @Nested
    class SearchCategoryForId {

        @Test
        @DisplayName("Deve buscar uma categoria por id")
        void shouldSearchCategoryForId() {
            //Arrange
            UUID id = UUID.randomUUID();
            Category category = new Category();
            CategoryResponse categoryResponse = new CategoryResponse(
                    id,
                    "Categoria 1"
            );

            //mocks
            when(categoryRepository.findById(id)).thenReturn(java.util.Optional.of(category));
            when(productMapper.categoryToCategoryResponse(category)).thenReturn(categoryResponse);

            // Act
            CategoryResponse result = categoryService.searchCategoryForId(id);

            // Assert
            assertNotNull(result);
            assertEquals(categoryResponse, result);
        }

        @Test
        @DisplayName("Deve lançar uma exceção ao tentar buscar uma categoria por id e não encontrar")
        void shouldThrowExceptionWhenSearchCategoryForIdNotFound() {
            // Arrange
            UUID id = UUID.randomUUID();

            //Mocks
            when(categoryRepository.findById(id)).thenReturn(Optional.empty());

            // Act and Assert
            assertThrows(CategoryNotFoundException.class, () -> categoryService.searchCategoryForId(id));
        }
    }

    @Nested
    class ListCategoryByName {

        @Test
        @DisplayName("Deve listar todas as categorias por nome")
        void shouldListAllCategoriesByName() {
            //Arrange
            String categoryName = "Categoria 1";
            Category category = new Category();
            CategoryResponse categoryResponse = new CategoryResponse(
                    UUID.randomUUID(),
                    "Categoria 1"
            );

            //Mocks
            when(categoryRepository.findByCategoryName(categoryName)).thenReturn(List.of(category));
            when(productMapper.categoryToCategoryResponse(category)).thenReturn(categoryResponse);
            //Act
            List<CategoryResponse> result = categoryService.listCategoryByName(categoryName);
            //Assert
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            assertEquals(categoryResponse, result.get(0));
        }

        @Test
        @DisplayName("Deve lançar uma exceção ao tentar listar todas as categorias por nome e não encontrar nenhuma")
        void shouldThrowExceptionWhenListByNameIsEmpty() {
            // Arrange
            String categoryName = "Categoria 1";
            when(categoryRepository.findByCategoryName(categoryName)).thenReturn(List.of());

            // Act and Assert
            CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> categoryService.listCategoryByName(categoryName));
            assertEquals("Categoria não encontrada com o nome: " + categoryName, exception.getMessage());
        }
    }

    @Nested
    class UpdateCategory {

        @Test
        @DisplayName("Deve atualizar uma categoria")
        void shouldUpdateCategory() {
            //Arrange
            UUID id = UUID.randomUUID();
            CategoryRequest categoryRequest = new CategoryRequest(
                    id,
                    "Categoria 1"
            );
            Category category = new Category();
            CategoryResponse categoryResponse = new CategoryResponse(
                    id,
                    "Categoria 1"
            );

            //Mocks
            doReturn(category).when(productMapper).categoryRequestToCategory(categoryRequest);
            doReturn(category).when(categoryRepository).save(category);
            doReturn(categoryResponse).when(productMapper).categoryToCategoryResponse(category);
            //Act
            CategoryResponse result = categoryService.updateCategory(id, categoryRequest);
            //Assert
            assertNotNull(result);
            assertEquals(categoryResponse, result);
        }

        @Test
        @DisplayName("Deve lançar uma exceção ao tentar atualizar uma categoria e não encontrar")
        void shouldThrowExceptionWhenUpdateCategoryNotFound() {
            // Arrange
            UUID id = UUID.randomUUID();
            CategoryRequest categoryRequest = new CategoryRequest(
                    id,
                    "Categoria 1"
            );

            //Mocks
            doReturn(null).when(productMapper).categoryRequestToCategory(categoryRequest);

            // Act and Assert
            assertThrows(NullPointerException.class, () -> categoryService.updateCategory(id, categoryRequest));
        }

        @Nested
        class DeleteCategory {

            @Test
            @DisplayName("Deve deletar uma categoria")
            void shouldDeleteCategory() {
                //Arrange
                UUID id = UUID.randomUUID();
                Category category = new Category();

                //Mocks
                when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
                //Act
                boolean result = categoryService.deleteCategory(id);
                //Assert
                assertTrue(result);
                //Verificar se o método foi chamado
                verify(categoryRepository).deleteById(id);
            }

            @Test
            @DisplayName("Deve lançar uma exceção ao tentar deletar uma categoria e não encontrar")
            void shouldThrowExceptionWhenDeleteCategoryNotFound() {
                // Arrange
                UUID id = UUID.randomUUID();

                //Mocks
                when(categoryRepository.findById(id)).thenReturn(Optional.empty());

                // Act and Assert
                assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(id));
            }
        }
    }
}