package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.MenuItemCreateDto;
import com.epam.smartkitchen.dto.UpdateMenuItemDto;
import com.epam.smartkitchen.dto.mapper.MenuItemMapper;
import com.epam.smartkitchen.models.MenuItem;
import com.epam.smartkitchen.repository.MenuRepository;
import com.epam.smartkitchen.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public UpdateMenuItemDto updateMenu(String id,MenuItem menuItem) {
        Optional<MenuItem> byId =menuRepository.findById(id);
        if (byId.isEmpty()){
            return null;
        }
        MenuItem menuItemFromDb = byId.get();
        menuItemFromDb.setName(menuItem.getName());
        menuItemFromDb.setPrice(menuItem.getPrice());
        menuItemFromDb.setImage(menuItem.getImage());
        menuItemFromDb.setWeight(menuItem.getWeight());
        menuItemFromDb.setmeasurement(menuItem.getmeasurement());
        menuItemFromDb.setProducts(menuItem.getProducts());

        return MenuItemMapper.menuItemToUpdateMenuDto(menuRepository.save(menuItemFromDb));
    }
}
