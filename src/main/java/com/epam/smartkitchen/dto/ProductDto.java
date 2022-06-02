package com.epam.smartkitchen.dto;

import com.epam.smartkitchen.models.Category;

import java.util.List;

public class ProductDto {

    private String name;
    private List<Category> categoryList;

    public ProductDto(){

    }

    public ProductDto(String name, List<Category> categoryList) {
        this.name = name;
        this.categoryList = categoryList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
