package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.CategoryDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;

public interface CategoryService {

    public Response<ErrorResponse, CategoryDto> add(CategoryDto categoryDto);


    void delete(String id);

    public Response<ErrorResponse, CategoryDto> update(CategoryDto categoryDto, String id);

    public Response<ErrorResponse, Page<CategoryDto>> getAll(Pageable pageable, boolean deleted);

    public Response<ErrorResponse, CategoryDto> getById(String id);

    Response<ErrorResponse, Page<CategoryDto>> exportExcel(HttpServletResponse httpResponse, Pageable pageable, boolean deleted);


}
