package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.ProductDto;
import com.epam.smartkitchen.models.Product;
import com.epam.smartkitchen.repository.ProductRepository;
import com.epam.smartkitchen.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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

    @BeforeEach
    public void setUp(){
        productRepository = mock(ProductRepository.class);
        mapper = mock(ModelMapper.class);
        productServiceTest = new ProductServiceImpl(productRepository,mapper);

    }

    @Test
    void addProduct() {
        ProductDto productDto = new ProductDto("pizza");
        Product product = new Product();
        when(mapper.map(productDto,Product.class)).thenReturn(product);
        ProductDto actualResult = productServiceTest.addProduct(productDto);
        assertEquals(productDto,actualResult);
        verify(productRepository,times(1)).save(product);
    }

    @Test
    void deleteProduct() {
        String productId = "test-id";
        Product product = new Product();

        when(productRepository.getById(productId)).thenReturn(product);

        productServiceTest.deleteProduct(productId);

        verify(productRepository,times(1)).save(product);
        assertTrue(product.getDeleted());
    }

    @Test
    void updateProduct(){
        ProductDto productDto = new ProductDto("pizza");
        Product product = new Product();
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        ProductDto actualResult = productServiceTest.updateProduct(productDto,"1");
        assertEquals(productDto,actualResult);
        verify(productRepository,times(1)).save(product);
    }
}