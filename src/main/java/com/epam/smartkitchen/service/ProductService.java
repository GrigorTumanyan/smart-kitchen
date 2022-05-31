package com.epam.smartkitchen.service;


import com.epam.smartkitchen.dto.ProductDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;

public interface ProductService {

    public Response<ErrorResponse,ProductDto> add(ProductDto productDto);

    void delete(String id);

    public Response<ErrorResponse,ProductDto> update(ProductDto productDto,String id);


    public Response<ErrorResponse, Page<ProductDto>>  getAll(Pageable pageable, boolean deleted);


    public Response<ErrorResponse,ProductDto> getById(String id);


    Response<ErrorResponse, Page<ProductDto>> exportExcel(HttpServletResponse httpResponse, Pageable pageable, boolean deleted);



}
