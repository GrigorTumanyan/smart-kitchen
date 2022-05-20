package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.warehouse.WarehouseDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/warehouse")
@RestController
public class WarehouseController {
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping("/")
    public ResponseEntity<Response<ErrorResponse, WarehouseDto>> addItem(@RequestBody WarehouseDto warehouseDto) {

        Response<ErrorResponse, WarehouseDto> warehouseDtoResponse = warehouseService.addItem(warehouseDto);

        return ResponseEntity.ok(warehouseDtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ErrorResponse, WarehouseDto>> updateItem(@PathVariable("id") String id, @RequestBody WarehouseDto warehouseDto) {
        Response<ErrorResponse, WarehouseDto> warehouseDtoResponse = warehouseService.updateItem(id, warehouseDto);
        return ResponseEntity.ok(warehouseDtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<ErrorResponse,WarehouseDto>> deleteItem (@PathVariable("id")String id){
        Response<ErrorResponse,WarehouseDto> warehouseDtoResponse =  warehouseService.deleteItemById(id);
        return ResponseEntity.noContent().build();
    }

}
