package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.CategoryDto;

public interface CategoryService {

    public CategoryDto addCategory(CategoryDto categoryDto);


    void deleteCategory(String id);

    public CategoryDto updateCategory(CategoryDto categoryDto, String id);

}
