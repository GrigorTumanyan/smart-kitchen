package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.ProductDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.models.Product;
import com.epam.smartkitchen.repository.ProductRepository;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.ExcelWriter;
import com.epam.smartkitchen.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    private ModelMapper mapper;
    private ProductService productServiceTest;
    private ExcelWriter excelWriter;


    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        mapper = mock(ModelMapper.class);
        excelWriter = mock(ExcelWriter.class);
        productServiceTest = new ProductServiceImpl(productRepository, mapper, excelWriter);

    }

    @Test
    void addProduct() {
        ProductDto productDto = new ProductDto("pizza");
        Product product = new Product();
        when(mapper.map(productDto, Product.class)).thenReturn(product);

        Response<ErrorResponse, ProductDto> actualResult = productServiceTest.add(productDto);

        assertEquals(actualResult.getSuccessObject(), productDto);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void deleteProduct() {
        String productId = "test-id";
        Product product = new Product();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productServiceTest.delete(productId);

        verify(productRepository, times(1)).save(product);
        assertTrue(product.getDeleted());
    }

    @Test
    void updateProduct() {
        ProductDto productDto = new ProductDto("pizza");
        Product product = new Product();
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        Response<ErrorResponse, ProductDto> actualResult = productServiceTest.update(productDto, "1");
        assertEquals(productDto, actualResult.getSuccessObject());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getAllProducts() {
        Pageable pageable = Pageable.ofSize(1);
        Product product = new Product();
        ProductDto productDto = new ProductDto();
        List<Product> productList = Arrays.asList(product);
        Page<Product> page = new PageImpl<>(productList, pageable, 1);
        Page<ProductDto> expectedResult = new PageImpl<>(Arrays.asList(productDto), pageable, page.getTotalElements());

        when(productRepository.findAllByDeleted(pageable, true)).thenReturn(page);
        when(mapper.map(product, ProductDto.class)).thenReturn(productDto);

        Response<ErrorResponse, Page<ProductDto>> actualResult = productServiceTest.getAll(pageable, true);

        assertEquals(expectedResult, actualResult.getSuccessObject());
    }


    @Test
    void getProductById() {
        ProductDto productDto = new ProductDto();
        Product product = new Product();
        String productId = "test-id";
        when(mapper.map(product, ProductDto.class)).thenReturn(productDto);
        when(productRepository.findByIdAndDeleted(productId, false)).thenReturn(Optional.of(product));
        Response<ErrorResponse, ProductDto> actual = productServiceTest.getById(productId);

        assertEquals(productDto, actual.getSuccessObject());
    }
}