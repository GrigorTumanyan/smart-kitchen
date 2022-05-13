package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.ProductDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<Response<ErrorResponse,ProductDto>> add(@RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.add(productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id){
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ErrorResponse,ProductDto>> update(@RequestBody ProductDto productDto,@PathVariable("id") String id){
        return ResponseEntity.ok(productService.update(productDto,id));
    }

    @GetMapping()
    public ResponseEntity<Response<ErrorResponse,Page<ProductDto>>> getAll(@RequestParam(
            value = "deleted",
            required = false,
            defaultValue = "false") boolean deleted,Pageable pageable){
        Response<ErrorResponse, Page<ProductDto>> all = productService.getAll(pageable, deleted);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/product/{id}")
    public ProductDto findProductById(@PathVariable String id){
        return productService.getProductById(id);
    }

}
