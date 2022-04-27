package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.ProductDto;
import com.epam.smartkitchen.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id){
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/product")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.addProduct(productDto));
    }
}
