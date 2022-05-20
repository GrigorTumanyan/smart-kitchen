package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.user.UserDto;
import com.epam.smartkitchen.dto.warehouse.OrderProductCount;
import com.epam.smartkitchen.dto.warehouse.WarehouseDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.models.Warehouse;
import com.epam.smartkitchen.response.Response;

import java.util.List;

public interface WarehouseService {
    Response<ErrorResponse, WarehouseDto> addItem(WarehouseDto warehouseDto);
    Response<ErrorResponse,WarehouseDto> updateItem(String id, WarehouseDto warehouseDto);
    Response<ErrorResponse,WarehouseDto> deleteItemById(String id);
    Response<ErrorResponse,WarehouseDto> decreaseProductCountInWarehouse(List<OrderProductCount> orderProductCounts);

}
