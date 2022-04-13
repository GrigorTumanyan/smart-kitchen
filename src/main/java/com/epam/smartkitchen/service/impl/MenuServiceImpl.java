package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.MenuItemCreateDto;
import com.epam.smartkitchen.dto.mapper.MenuItemMapper;
import com.epam.smartkitchen.models.MenuItem;
import com.epam.smartkitchen.repository.MenuRepository;
import com.epam.smartkitchen.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;

    }


    @Override
    public MenuItemCreateDto addMenu(MenuItem menuItem) {

        return MenuItemMapper.menuItemToDto(menuRepository.save(menuItem));

    }
}
