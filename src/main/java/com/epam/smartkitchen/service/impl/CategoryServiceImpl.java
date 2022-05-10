package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.CategoryDto;
import com.epam.smartkitchen.models.Category;
import com.epam.smartkitchen.repository.CategoryRepository;
import com.epam.smartkitchen.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto,Category.class);
        categoryRepository.save(category);
        return categoryDto;
    }

    @Override
    public void deleteCategory(String id) {
        Category category = categoryRepository.getById(id);
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        mapper.map(categoryDto, category);
        categoryRepository.save(category);
        return categoryDto;
    }

    @Override
    public Page<CategoryDto> getAllCategories(Pageable pageable, boolean deleted) {
        Page<Category> page = categoryRepository.findAllByDeleted(pageable,deleted);
        List<Category> categoryList = page.getContent();
        List<CategoryDto> categoryDtos = new ArrayList();
        for (Category category:categoryList) {
            CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
            categoryDtos.add(categoryDto);
        }
        return new PageImpl<>(categoryDtos,pageable,page.getTotalElements());
    }

}
