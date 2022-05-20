package com.epam.smartkitchen.dto.mapper;

import com.epam.smartkitchen.dto.menuItem.MenuItemCreateDto;
import com.epam.smartkitchen.dto.menuItem.UpdateMenuItemDto;
import com.epam.smartkitchen.models.MenuItem;

public class MenuItemMapper {

    private MenuItemMapper() {
        throw new IllegalStateException("Can not create an object of utility class.");
    }


    public static MenuItem dtoToMenuItem(MenuItemCreateDto menuItemCreateDto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemCreateDto.getName());
        menuItem.setPrice(menuItemCreateDto.getPrice());
        menuItem.setImage(menuItemCreateDto.getImage());
        menuItem.setWeight(menuItemCreateDto.getWeight());
        menuItem.setmeasurement(menuItemCreateDto.getMeasurement());
        menuItem.setProducts(menuItemCreateDto.getProducts());

        return menuItem;

    }

    public static MenuItemCreateDto menuItemToDto(MenuItem menuItem) {
        MenuItemCreateDto menuItemCreateDto = new MenuItemCreateDto();
        menuItemCreateDto.setName(menuItem.getName());
        menuItemCreateDto.setPrice(menuItem.getPrice());
        menuItemCreateDto.setImage(menuItem.getImage());
        menuItemCreateDto.setWeight(menuItem.getWeight());
        menuItemCreateDto.setMeasurement(menuItem.getmeasurement());
        menuItemCreateDto.setProducts(menuItem.getProducts());

        return menuItemCreateDto;

    }

    public static MenuItem dtoToMenuItemUpdate(UpdateMenuItemDto updateMenuItemDto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(updateMenuItemDto.getName());
        menuItem.setPrice(updateMenuItemDto.getPrice());
        menuItem.setImage(updateMenuItemDto.getImage());
        menuItem.setWeight(updateMenuItemDto.getWeight());
        menuItem.setmeasurement(updateMenuItemDto.getMeasurement());
        menuItem.setProducts(updateMenuItemDto.getProducts());

        return menuItem;
    }

    public static UpdateMenuItemDto menuItemToUpdateMenuDto(MenuItem menuItem) {
        UpdateMenuItemDto updateMenuItemDto = new UpdateMenuItemDto();
        updateMenuItemDto.setName(menuItem.getName());
        updateMenuItemDto.setPrice(menuItem.getPrice());
        updateMenuItemDto.setImage(menuItem.getImage());
        updateMenuItemDto.setWeight(menuItem.getWeight());
        updateMenuItemDto.setMeasurement(menuItem.getmeasurement());
        updateMenuItemDto.setProducts(menuItem.getProducts());
        updateMenuItemDto.setDeleted(menuItem.getDeleted());

        return updateMenuItemDto;
    }
}
