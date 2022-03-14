package com.epam.smartkitchen.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product extends BaseEntity {
    @Column
    String name;
    @ManyToMany
    List<Category> category;

    public Product() {
    }

    public Product(String name, List<Category> category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }
}
