package com.epam.smartkitchen.dto.warehouse;

import com.epam.smartkitchen.dto.ProductDto;
import com.epam.smartkitchen.models.Product;

import java.util.Objects;

public class WarehouseDto {
    private String description;
    private Double count;
    private Double price;
    private String measurement;
    private ProductDto productDto;
    private Boolean deleted = Boolean.FALSE;

    public WarehouseDto(String description, Double count, Double price, String measurement, ProductDto productDto, Boolean deleted) {
        this.description = description;
        this.count = count;
        this.price = price;
        this.measurement = measurement;
        this.productDto = productDto;
        this.deleted = deleted;
    }

    public WarehouseDto() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseDto that = (WarehouseDto) o;
        return Objects.equals(description, that.description) && Objects.equals(count, that.count) && Objects.equals(price, that.price) && Objects.equals(measurement, that.measurement) && Objects.equals(productDto, that.productDto) && Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, count, price, measurement, productDto, deleted);
    }

    @Override
    public String toString() {
        return "WarehouseDto{" +
                "description='" + description + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", measurement='" + measurement + '\'' +
                ", productDto=" + productDto +
                ", deleted=" + deleted +
                '}';
    }
}