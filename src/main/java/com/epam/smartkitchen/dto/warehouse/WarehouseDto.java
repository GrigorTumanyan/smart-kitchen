package com.epam.smartkitchen.dto.warehouse;

import com.epam.smartkitchen.models.Product;

import java.util.Objects;

public class WarehouseDto {
    private String description;
    private Double count;
    private Double price;
    private String measurement;
    private Product product;

    public WarehouseDto(String description, Double count, Double price, String measurement, Product product) {
        this.description = description;
        this.count = count;
        this.price = price;
        this.measurement = measurement;
        this.product = product;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseDto that = (WarehouseDto) o;
        return Objects.equals(description, that.description) && Objects.equals(count, that.count) && Objects.equals(price, that.price) && Objects.equals(measurement, that.measurement) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, count, price, measurement, product);
    }

    @Override
    public String toString() {
        return "WarehouseDto{" +
                "description='" + description + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", measurement='" + measurement + '\'' +
                ", product=" + product +
                '}';
    }
}