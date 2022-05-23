package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.menuItem.MenuItemCreateDto;
import com.epam.smartkitchen.dto.menuItem.UpdateMenuItemDto;
import com.epam.smartkitchen.dto.mapper.MenuItemMapper;
import com.epam.smartkitchen.models.Category;
import com.epam.smartkitchen.models.MenuItem;
import com.epam.smartkitchen.models.Product;
import com.epam.smartkitchen.models.Warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        menuItem.setDeleted(true);
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
    protected static Optional<MenuItem> optionalMenuItem(){
        MenuItem menuItem = menuItem();
       return Optional.of(menuItem);



    }



    protected static UpdateMenuItemDto updateMenuItemDto(){
        return MenuItemMapper.menuItemToUpdateMenuDto(menuItem());
    }

    protected static Warehouse toWarehouse(){
        Warehouse warehouse = new Warehouse();

        warehouse.setDescription("asdas");
        warehouse.setPrice(100.0);
        warehouse.setCount(15.0);
        warehouse.setMeasurement("piece");
        warehouse.setDeleted(false);
        warehouse.setProduct(null);
        return  warehouse;
    }
    protected  static Optional<Warehouse> optionalWarehouse(){
        Warehouse warehouse = toWarehouse();
        return Optional.of(warehouse);
    }

}
