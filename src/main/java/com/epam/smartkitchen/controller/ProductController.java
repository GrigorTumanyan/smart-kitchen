package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.ProductDto;
import com.epam.smartkitchen.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ProductDto addProduct(@RequestBody ProductDto productDto){
        return productService.addProduct(productDto);
    }
}
