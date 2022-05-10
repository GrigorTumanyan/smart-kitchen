package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.ProductDto;
import com.epam.smartkitchen.exceptions.ResourceExistException;
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

    @Override
    public void deleteProduct(String id) {
        Product product = productRepository.getById(id);
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto,String id) {
        Product product = productRepository.findById(id).orElseThrow();
        mapper.map(productDto, product);
        productRepository.save(product);
        return productDto;
    }

    @Override
    public ProductDto getProductById(String id){
        Product product = productRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new ResourceExistException("product isn't found"));
        return mapper.map(product, ProductDto.class);
    }
}
