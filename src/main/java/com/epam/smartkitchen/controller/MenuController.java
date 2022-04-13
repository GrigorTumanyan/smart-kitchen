package com.epam.smartkitchen.controller;


import com.epam.smartkitchen.dto.MenuItemCreateDto;
import com.epam.smartkitchen.models.MenuItem;
import com.epam.smartkitchen.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/addMenu")
    public ResponseEntity<MenuItemCreateDto> addMenuItem(@RequestBody MenuItem menuItem) {
        try {
            return new  ResponseEntity<>(menuService.addMenu(menuItem),HttpStatus.CREATED);
        }
    catch (Exception e){
            return new  ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }
}
