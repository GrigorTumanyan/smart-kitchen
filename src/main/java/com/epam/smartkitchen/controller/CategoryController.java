package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.CategoryDto;
import com.epam.smartkitchen.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category/add")
    public CategoryDto addProduct(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }
}
