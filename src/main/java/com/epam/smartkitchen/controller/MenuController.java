package com.epam.smartkitchen.controller;


import com.epam.smartkitchen.dto.menuItem.MenuItemCreateDto;
import com.epam.smartkitchen.dto.menuItem.UpdateMenuItemDto;
import com.epam.smartkitchen.models.MenuItem;
import com.epam.smartkitchen.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/menu")
@RestController
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/")
    public ResponseEntity<MenuItemCreateDto> addMenuItem(@RequestBody MenuItem menuItem) {
        try {
            return new ResponseEntity<>(menuService.addMenu(menuItem), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateMenuItemDto> updateMenuItem(@PathVariable(name = "id") String id, @RequestBody UpdateMenuItemDto updateMenuItemDto) {
        UpdateMenuItemDto savedUpdateMenuItemDto = menuService.updateMenu(id, updateMenuItemDto);
        if (updateMenuItemDto == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            return new ResponseEntity<>(savedUpdateMenuItemDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable(name = "id") String id) {
        try {
            menuService.deleteMenuItemById(id);
        } catch (Exception e) {
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
