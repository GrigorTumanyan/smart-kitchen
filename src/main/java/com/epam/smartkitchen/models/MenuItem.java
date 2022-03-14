package com.epam.smartkitchen.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class MenuItem extends BaseEntity {
    @Column
    String name;
    @Column
    String image;
    @Column
    Double weight;
    @Column
    Double price;
    @Column
    String measurement;
    @ManyToMany
    List<Product> products;

    public MenuItem(String name, String image, Double weight, Double price, String measurement, List<Product> products) {
        this.name = name;
        this.image = image;
        this.weight = weight;
        this.price = price;
        this.measurement = measurement;
        this.products = products;
    }

    public MenuItem() {
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

    public String getmeasurement() {
        return measurement;
    }

    public void setmeasurement(String measurement) {
        this.measurement = measurement;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
