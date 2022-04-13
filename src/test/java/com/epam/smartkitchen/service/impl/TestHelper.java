package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.MenuItemCreateDto;
import com.epam.smartkitchen.models.Category;
import com.epam.smartkitchen.models.MenuItem;
import com.epam.smartkitchen.models.Product;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    protected static MenuItemCreateDto menuItemCreateDto(){
        MenuItemCreateDto menuItemCreateDto = new MenuItemCreateDto();
        menuItemCreateDto.setName("palmetto");
        menuItemCreateDto.setPrice(100.0);
        menuItemCreateDto.setWeight(15.0);
        menuItemCreateDto.setMeasurement("piece");
        menuItemCreateDto.setImage("C:\\Users\\DEll\\IdeaProjects\\smart-kitchen1\\src\\main\\java\\com\\epam\\smartkitchen\\controller");
        menuItemCreateDto.setProducts(generateProductsList());
        return  menuItemCreateDto;
    }
    protected static MenuItem menuItem(){
        MenuItem menuItem = new MenuItem();
        menuItem.setName("palmetto");
        menuItem.setPrice(100.0);
        menuItem.setWeight(15.0);
        menuItem.setmeasurement("piece");
        menuItem.setImage("C:\\Users\\DEll\\IdeaProjects\\smart-kitchen1\\src\\main\\java\\com\\epam\\smartkitchen\\controller");
        menuItem.setProducts(generateProductsList());
        return  menuItem;
    }

    private static List<Product> generateProductsList(){
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setName("sausage");
        product.setCategory(generateCategory());
        products.add(product);
        return products;
    }
    private static List<Category> generateCategory(){
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setName("Meat");
        categories.add(category);
        return categories;
    }

}
