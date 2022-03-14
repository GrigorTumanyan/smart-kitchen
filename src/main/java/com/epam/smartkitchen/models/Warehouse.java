package com.epam.smartkitchen.models;

import com.google.inject.multibindings.StringMapKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Warehouse extends BaseEntity {

    @Column
    String name;
    @Column
    Integer count;
    @Column
    Double price;
    @Column
    String measurement;
    @OneToMany
    List<Product> productList;

    public Warehouse() {
    }

    public Warehouse(String name, Integer count, Double price, String measurement, List<Product> productList) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.measurement = measurement;
        this.productList = productList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
