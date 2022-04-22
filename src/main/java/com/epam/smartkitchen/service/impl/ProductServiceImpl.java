package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.ProductDto;
import com.epam.smartkitchen.models.Product;
import com.epam.smartkitchen.repository.ProductRepository;
import com.epam.smartkitchen.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto){
        Product product = mapper.map(productDto, Product.class);
        productRepository.save(product);
        return productDto;
    }
}
