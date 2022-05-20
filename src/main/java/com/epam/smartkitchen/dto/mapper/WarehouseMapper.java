package com.epam.smartkitchen.dto.mapper;

import com.epam.smartkitchen.dto.warehouse.WarehouseDto;
import com.epam.smartkitchen.models.Warehouse;

public class WarehouseMapper {
    private WarehouseMapper() {
        throw new IllegalStateException("Can not create an object of utility class.");
    }

    public static Warehouse warehouseDtoToWarehouse(WarehouseDto warehouseDto){
        Warehouse warehouse = new Warehouse();
        warehouse.setDescription(warehouseDto.getDescription());
        warehouse.setCount(warehouseDto.getCount());
        warehouse.setPrice(warehouseDto.getPrice());
        warehouse.setMeasurement(warehouseDto.getMeasurement());
        warehouse.setProduct(warehouseDto.getProduct());

        return warehouse;
    }

    public static WarehouseDto warehouseToWarehouseDto(Warehouse warehouse){
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setDescription(warehouse.getDescription());
        warehouseDto.setCount(warehouse.getCount());
        warehouseDto.setPrice(warehouse.getPrice());
        warehouseDto.setMeasurement(warehouse.getMeasurement());
        warehouseDto.setProduct(warehouse.getProduct());

        return warehouseDto;
    }

}
