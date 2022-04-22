package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.MenuItemCreateDto;
import com.epam.smartkitchen.dto.UpdateMenuItemDto;
import com.epam.smartkitchen.models.MenuItem;


public interface MenuService {
    MenuItemCreateDto addMenu(MenuItem menuItem);
    UpdateMenuItemDto updateMenu(String id,UpdateMenuItemDto updateMenuItemDto);
}
