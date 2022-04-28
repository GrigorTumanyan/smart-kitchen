package com.epam.smartkitchen.service;


import com.epam.smartkitchen.dto.ProductDto;

public interface ProductService {

    public ProductDto addProduct(ProductDto productDto);

    void deleteProduct(String id);

    public ProductDto updateProduct(ProductDto productDto,String id);

}
