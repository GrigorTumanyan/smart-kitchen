package com.epam.smartkitchen.dto;

public class ProductDto {

    private String id;
    private String name;

    public ProductDto(){

    }

    public ProductDto(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
