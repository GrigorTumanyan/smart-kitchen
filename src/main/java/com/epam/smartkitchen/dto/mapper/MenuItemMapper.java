package com.epam.smartkitchen.dto.mapper;

import com.epam.smartkitchen.dto.MenuItemCreateDto;
import com.epam.smartkitchen.models.MenuItem;

public class MenuItemMapper {

    private MenuItemMapper(){
        throw new IllegalStateException("Can not create an object of utility class.");
    }

    public static MenuItem dtoToMenuItem(MenuItemCreateDto menuItemCreateDto){
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemCreateDto.getName());
        menuItem.setPrice(menuItemCreateDto.getPrice());
        menuItem.setImage(menuItemCreateDto.getImage());
        menuItem.setWeight(menuItemCreateDto.getWeight());
        menuItem.setmeasurement(menuItemCreateDto.getMeasurement());
        menuItem.setProducts(menuItemCreateDto.getProducts());

        return menuItem;

    }
    public static MenuItemCreateDto menuItemToDto (MenuItem menuItem){
        MenuItemCreateDto menuItemCreateDto = new MenuItemCreateDto();
        menuItemCreateDto.setName(menuItem.getName());
        menuItemCreateDto.setPrice(menuItem.getPrice());
        menuItemCreateDto.setImage(menuItem.getImage());
        menuItemCreateDto.setWeight(menuItem.getWeight());
        menuItemCreateDto.setMeasurement(menuItem.getmeasurement());
        menuItemCreateDto.setProducts(menuItem.getProducts());

        return menuItemCreateDto;

    }
}
