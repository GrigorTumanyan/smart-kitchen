package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.ProductDto;
import com.epam.smartkitchen.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable("id") String id){
        return ResponseEntity.ok(productService.updateProduct(productDto,id));
    }

    @GetMapping("/product")
    public Page<ProductDto> getAllProducts(@RequestParam("deleted") boolean deleted,
                                           Pageable pageable){
        return productService.getAllProducts(pageable,deleted);
    }


}
