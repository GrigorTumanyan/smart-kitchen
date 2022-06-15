package com.epam.smartkitchen.dto.menuItem;


import com.epam.smartkitchen.models.Product;

import java.util.List;
import java.util.Objects;

public class MenuItemCreateDto {


    private String name;
    private String image;
    private Double weight;
    private Double price;
    private String measurement;
    private List<Product> products;

    public MenuItemCreateDto(String name, String image, Double weight, Double price, String measurement, List<Product> products) {

        this.name = name;
        this.image = image;
        this.weight = weight;
        this.price = price;
        this.measurement = measurement;
        this.products = products;
    }

    public MenuItemCreateDto() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemCreateDto that = (MenuItemCreateDto) o;
        return Objects.equals(name, that.name) && Objects.equals(image, that.image) && Objects.equals(weight, that.weight) && Objects.equals(price, that.price) && Objects.equals(measurement, that.measurement) && Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, image, weight, price, measurement, products);
    }

    @Override
    public String toString() {
        return "MenuItemCreateDto{" +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", measurement='" + measurement + '\'' +
                ", products=" + products +
                '}';
    }
}
