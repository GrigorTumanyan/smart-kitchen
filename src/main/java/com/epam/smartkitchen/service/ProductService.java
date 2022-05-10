package com.epam.smartkitchen.service;


import com.epam.smartkitchen.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    public ProductDto addProduct(ProductDto productDto);

    void deleteProduct(String id);

    public ProductDto updateProduct(ProductDto productDto,String id);


    public Page<ProductDto> getAllProducts(Pageable pageable, boolean deleted);


}
