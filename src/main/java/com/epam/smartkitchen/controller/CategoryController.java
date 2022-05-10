package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.CategoryDto;
import com.epam.smartkitchen.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") String id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable("id") String id){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto,id));
    }

    @GetMapping("/category/{id}")
    public CategoryDto findCategoryById(@PathVariable String id){
        return categoryService.getCategoryById(id);
    }
}
