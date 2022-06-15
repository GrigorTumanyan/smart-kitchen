package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.menuItem.MenuItemCreateDto;
import com.epam.smartkitchen.dto.menuItem.UpdateMenuItemDto;
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
    public UpdateMenuItemDto updateMenu(String id,UpdateMenuItemDto updateMenuItemDto) {
        Optional<MenuItem> byId =menuRepository.findById(id);
        if (byId.isEmpty()){
            return null;
        }
        MenuItem menuItemFromDb = byId.get();
        menuItemFromDb.setName(updateMenuItemDto.getName());
        menuItemFromDb.setPrice(updateMenuItemDto.getPrice());
        menuItemFromDb.setImage(updateMenuItemDto.getImage());
        menuItemFromDb.setWeight(updateMenuItemDto.getWeight());
        menuItemFromDb.setmeasurement(updateMenuItemDto.getMeasurement());
        menuItemFromDb.setProducts(updateMenuItemDto.getProducts());

        return MenuItemMapper.menuItemToUpdateMenuDto(menuRepository.save(menuItemFromDb));
    }

    @Override
    public UpdateMenuItemDto deleteMenuItemById(String id) {
        MenuItem menuItem = menuRepository.findById(id).orElseThrow();
        menuItem.setDeleted(true);
        MenuItem save = menuRepository.save(menuItem);
        return MenuItemMapper.menuItemToUpdateMenuDto(save);

    }
}
