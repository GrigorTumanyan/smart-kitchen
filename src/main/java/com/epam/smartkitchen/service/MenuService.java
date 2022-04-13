package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.MenuItemCreateDto;
import com.epam.smartkitchen.models.MenuItem;


public interface MenuService {
    MenuItemCreateDto addMenu(MenuItem menuItem);
}
