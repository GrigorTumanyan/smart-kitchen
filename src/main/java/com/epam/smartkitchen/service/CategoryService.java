package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    public CategoryDto addCategory(CategoryDto categoryDto);


    void deleteCategory(String id);

    public CategoryDto updateCategory(CategoryDto categoryDto, String id);

    public Page<CategoryDto> getAllCategories(Pageable pageable, boolean deleted);


}
