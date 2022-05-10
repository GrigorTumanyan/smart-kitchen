package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.CategoryDto;
import com.epam.smartkitchen.models.Category;
import com.epam.smartkitchen.repository.CategoryRepository;
import com.epam.smartkitchen.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    private ModelMapper mapper;
    private CategoryService categoryServiceTest;

    @BeforeEach
    public void setUp(){
        categoryRepository = mock(CategoryRepository.class);
        mapper = mock(ModelMapper.class);
        categoryServiceTest = new CategoryServiceImpl(categoryRepository,mapper);

    }

    @Test
    void addCategory() {
        CategoryDto categoryDto = new CategoryDto();
        Category category = new Category();
        when(mapper.map(categoryDto,Category.class)).thenReturn(category);
        CategoryDto actualResult = categoryServiceTest.addCategory(categoryDto);
        assertEquals(categoryDto,actualResult);
        verify(categoryRepository,times(1)).save(category);
    }

    @Test
    void deleteCategory() {
        String categoryId = "test-id";

        Category category = new Category();
        when(categoryRepository.getById(categoryId)).thenReturn(category);

        categoryServiceTest.deleteCategory(categoryId);

        verify(categoryRepository,times(1)).save(category);
        assertTrue(category.getDeleted());
    }

    @Test
    void updateCategory(){
        CategoryDto categoryDto = new CategoryDto("pizza");
        Category category = new Category();
        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));
        CategoryDto actualResult = categoryServiceTest.updateCategory(categoryDto,"1");
        assertEquals(categoryDto,actualResult);
        verify(categoryRepository,times(1)).save(category);
    }

    @Test
    void getAllCategories() {
        Pageable pageable = Pageable.ofSize(1);
        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto();
        List<Category> categoryList = Arrays.asList(category);
        Page<Category> page = new PageImpl<>(categoryList,pageable,1);
        Page<CategoryDto> expectedResult = new PageImpl<>(Arrays.asList(categoryDto), pageable, page.getTotalElements());

        when(categoryRepository.findAllByDeleted(pageable,true)).thenReturn(page);
        when(mapper.map(category, CategoryDto.class)).thenReturn(categoryDto);

        Page<CategoryDto> actualResult = categoryServiceTest.getAllCategories(pageable,true);

        assertEquals(expectedResult,actualResult);
    }
}