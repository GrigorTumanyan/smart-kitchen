//package com.epam.smartkitchen.service.impl;
//
//import com.epam.smartkitchen.dto.CategoryDto;
//import com.epam.smartkitchen.exceptions.ErrorResponse;
//import com.epam.smartkitchen.models.Category;
//import com.epam.smartkitchen.repository.CategoryRepository;
//import com.epam.smartkitchen.response.Response;
//import com.epam.smartkitchen.service.CategoryService;
//import com.epam.smartkitchen.service.ExcelWriter;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.modelmapper.ModelMapper;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//class CategoryServiceImplTest {
//
//    @Mock
//    private CategoryRepository categoryRepository;
//    private ModelMapper mapper;
//    private CategoryService categoryServiceTest;
//    private ExcelWriter excelWriter;
//
//
//    @BeforeEach
//    public void setUp(){
//        categoryRepository = mock(CategoryRepository.class);
//        mapper = mock(ModelMapper.class);
//        excelWriter = mock(ExcelWriter.class);
//        categoryServiceTest = new CategoryServiceImpl(categoryRepository,mapper, excelWriter);
//
//    }
//
//    @Test
//    void addCategory() {
//        CategoryDto categoryDto = new CategoryDto();
//        Category category = new Category();
//        when(mapper.map(categoryDto,Category.class)).thenReturn(category);
//        Response<ErrorResponse, CategoryDto> actualResult = categoryServiceTest.add(categoryDto);
//        assertEquals(categoryDto,actualResult.getSuccessObject());
//        verify(categoryRepository,times(1)).save(category);
//    }
//
//    @Test
//    void deleteCategory() {
//        String categoryId = "test-id";
//
//        Category category = new Category();
//        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
//
//        categoryServiceTest.delete(categoryId);
//
//        verify(categoryRepository,times(1)).save(category);
//        assertTrue(category.getDeleted());
//    }
//
//    @Test
//    void updateCategory(){
//        CategoryDto categoryDto = new CategoryDto("pizza");
//        Category category = new Category();
//        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));
//        Response<ErrorResponse, CategoryDto> actualResult = categoryServiceTest.update(categoryDto,"1");
//        assertEquals(categoryDto,actualResult.getSuccessObject());
//        verify(categoryRepository,times(1)).save(category);
//    }
//
//    @Test
//    void getAllCategories() {
//        Pageable pageable = Pageable.ofSize(1);
//        Category category = new Category();
//        CategoryDto categoryDto = new CategoryDto();
//        List<Category> categoryList = Arrays.asList(category);
//        Page<Category> page = new PageImpl<>(categoryList,pageable,1);
//        Page<CategoryDto> expectedResult = new PageImpl<>(Arrays.asList(categoryDto), pageable, page.getTotalElements());
//
//        when(categoryRepository.findAllByDeleted(pageable,true)).thenReturn(page);
//        when(mapper.map(category, CategoryDto.class)).thenReturn(categoryDto);
//
//        Response<ErrorResponse, Page<CategoryDto>> actualResult = categoryServiceTest.getAll(pageable,true);
//
//        assertEquals(expectedResult,actualResult.getSuccessObject());
//    }
//
//    @Test
//    void getProductById() {
//        CategoryDto categoryDto = new CategoryDto();
//        Category category = new Category();
//        String categoryId = "test-id";
//        when(mapper.map(category, CategoryDto.class)).thenReturn(categoryDto);
//        when(categoryRepository.findByIdAndDeleted(categoryId,false)).thenReturn(Optional.of(category));
//        Response<ErrorResponse, CategoryDto> actual = categoryServiceTest.getById(categoryId);
//
//        assertEquals(categoryDto,actual.getSuccessObject());
//    }
//}