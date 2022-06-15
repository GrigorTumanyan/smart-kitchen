package com.epam.smartkitchen.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Warehouse extends BaseEntity {

    @Column
    String description;
    @Column
    Double count;
    @Column
    Double price;
    @Column
    String measurement;
    @ManyToOne
    Product product;

    public Warehouse() {
    }

    public Warehouse(String description, Double count, Double price, String measurement, Product product) {
        this.description = description;
        this.count = count;
        this.price = price;
        this.measurement = measurement;
        this.product = product;
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
}
