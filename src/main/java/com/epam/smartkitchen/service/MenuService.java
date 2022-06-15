package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.menuItem.MenuItemCreateDto;
import com.epam.smartkitchen.dto.menuItem.UpdateMenuItemDto;
import com.epam.smartkitchen.models.MenuItem;


public interface MenuService {
    MenuItemCreateDto addMenu(MenuItem menuItem);
    UpdateMenuItemDto updateMenu(String id,UpdateMenuItemDto updateMenuItemDto);

    UpdateMenuItemDto deleteMenuItemById(String id);
}
