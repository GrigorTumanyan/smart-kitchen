package com.epam.smartkitchen.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Category extends BaseEntity{

    @Column
    String name;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
