package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.ProductDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.exceptions.RecordNotFoundException;
import com.epam.smartkitchen.models.Product;
import com.epam.smartkitchen.repository.ProductRepository;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.ExcelWriter;
import com.epam.smartkitchen.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    private final ExcelWriter excelWriter;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper, ExcelWriter excelWriter) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.excelWriter = excelWriter;
    }

    @Override
    public Response<ErrorResponse,ProductDto> add(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);
        productRepository.save(product);
        return new Response<>(null,productDto,ProductDto.class.getSimpleName());
    }

    @Override
    public void delete(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("product with " + id + " id is not found"));
        product.setDeleted(true);
        productRepository.save(product);

    }

    @Override
    public Response<ErrorResponse,ProductDto> update(ProductDto productDto, String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("product with " + id + " id is not found"));
        mapper.map(productDto, product);
        productRepository.save(product);
        return new Response<>(null,productDto,ProductDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, Page<ProductDto>> getAll(Pageable pageable, boolean deleted) {
        Page<Product> page = productRepository.findAllByDeleted(pageable, deleted);
        List<Product> productList = page.getContent();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : productList) {
            ProductDto productDto = mapper.map(product, ProductDto.class);
            productDtos.add(productDto);
        }
        Page<ProductDto> productDtos1 = new PageImpl<>(productDtos, pageable, page.getTotalElements());
        return new Response<>(null, productDtos1, ProductDto.class.getSimpleName());

    }



    @Override
    public Response<ErrorResponse,ProductDto> getById(String id){
        Product product = productRepository.findByIdAndDeleted(id, false)
                .orElseThrow(() -> new RecordNotFoundException("product with " + id + " id is not found"));
        ProductDto map = mapper.map(product, ProductDto.class);
        return new Response<>(null,map,ProductDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, Page<ProductDto>> exportExcel(HttpServletResponse httpResponse, Pageable pageable, boolean deleted) {
        Response<ErrorResponse, Page<ProductDto>> response;
        response = getAll(pageable,deleted);
        List<ProductDto> productDtoList = new LinkedList<>();
        for (ProductDto productDto : response.getSuccessObject()) {
            productDtoList.add(productDto);
        }

        excelWriter.write(productDtoList, httpResponse);
        return response;
    }
}
