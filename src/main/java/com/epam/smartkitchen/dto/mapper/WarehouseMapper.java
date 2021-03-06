package com.epam.smartkitchen.dto.mapper;

import com.epam.smartkitchen.dto.ProductDto;
import com.epam.smartkitchen.dto.warehouse.WarehouseDto;
import com.epam.smartkitchen.models.Product;
import com.epam.smartkitchen.models.Warehouse;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class WarehouseMapper {
    private WarehouseMapper() {
        throw new IllegalStateException("Can not create an object of utility class.");
    }

    public static Warehouse warehouseDtoToWarehouse(WarehouseDto warehouseDto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setDescription(warehouseDto.getDescription());
        warehouse.setCount(warehouseDto.getCount());
        warehouse.setPrice(warehouseDto.getPrice());
        warehouse.setMeasurement(warehouseDto.getMeasurement());
        ProductDto productDto = warehouseDto.getProductDto();
        Product product = new Product(productDto.getName(), productDto.getCategoryList());
        warehouse.setProduct(product);
        warehouse.setDeleted(warehouseDto.getDeleted());

        return warehouse;
    }

    public static WarehouseDto warehouseToWarehouseDto(Warehouse warehouse) {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setDescription(warehouse.getDescription());
        warehouseDto.setCount(warehouse.getCount());
        warehouseDto.setPrice(warehouse.getPrice());
        warehouseDto.setMeasurement(warehouse.getMeasurement());
        Product product = warehouse.getProduct();
        ProductDto productDto = new ProductDto(product.getName(), product.getCategory());
        warehouseDto.setProductDto(productDto);
        warehouseDto.setDeleted(warehouse.getDeleted());

        return warehouseDto;
    }

    public static List<WarehouseDto> warehouseListToWarehouseDtoList(Page<Warehouse> warehouseList) {
        List<WarehouseDto> warehouseDtoList = new ArrayList<>();
        for (Warehouse warehouse : warehouseList) {
            WarehouseDto warehouseDto = warehouseToWarehouseDto(warehouse);
            warehouseDtoList.add(warehouseDto);
        }
        return warehouseDtoList;
    }
}
