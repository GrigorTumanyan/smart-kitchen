package com.epam.smartkitchen.controller;


import com.epam.smartkitchen.dto.MenuItemCreateDto;
import com.epam.smartkitchen.dto.UpdateMenuItemDto;
import com.epam.smartkitchen.models.MenuItem;
import com.epam.smartkitchen.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/updateMenu/{id}")
    public ResponseEntity<UpdateMenuItemDto> updateMenuItem(@PathVariable(name = "id") String id, @RequestBody MenuItem menuItem){
        UpdateMenuItemDto updateMenuItemDto = menuService.updateMenu(id, menuItem);
        if(updateMenuItemDto == null){
            return ResponseEntity.notFound().build();
        }
           try{
        return new ResponseEntity<> (updateMenuItemDto,HttpStatus.CREATED);
    }
       catch (Exception e){
           return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}
