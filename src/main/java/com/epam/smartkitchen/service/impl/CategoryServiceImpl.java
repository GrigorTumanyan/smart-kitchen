package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.CategoryDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.exceptions.RecordNotFoundException;
import com.epam.smartkitchen.models.Category;
import com.epam.smartkitchen.repository.CategoryRepository;
import com.epam.smartkitchen.response.Response;
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
    public Response<ErrorResponse, CategoryDto> add(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto,Category.class);
        categoryRepository.save(category);
        return new Response<>(null,categoryDto,CategoryDto.class.getSimpleName());
    }

    @Override
    public void delete(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("category with " + id + " id is not found"));
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    @Override
    public Response<ErrorResponse,CategoryDto> update(CategoryDto categoryDto, String id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("category with " + id + " id is not found"));
        mapper.map(categoryDto, category);
        categoryRepository.save(category);
        return new Response<>(null,categoryDto,CategoryDto.class.getSimpleName());
    }

    @Override
    public  Response<ErrorResponse, Page<CategoryDto>> getAll(Pageable pageable, boolean deleted) {
        Page<Category> page = categoryRepository.findAllByDeleted(pageable,deleted);
        List<Category> categoryList = page.getContent();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category:categoryList) {
            CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
            categoryDtos.add(categoryDto);
        }
        PageImpl<CategoryDto> categoryDtos1 = new PageImpl<>(categoryDtos, pageable, page.getTotalElements());
        return new Response<>(null,categoryDtos1,CategoryDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, CategoryDto> getById(String id){
        Category category = categoryRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new RecordNotFoundException("category with " + id + " id is not found"));
        CategoryDto map = mapper.map(category, CategoryDto.class);
        return new Response<>(null,map,CategoryDto.class.getSimpleName());
    }

}
