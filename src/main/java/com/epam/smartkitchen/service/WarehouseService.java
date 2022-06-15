package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.warehouse.OrderProductCountDto;
import com.epam.smartkitchen.dto.warehouse.WarehouseDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;

import java.util.List;

public interface WarehouseService {
    Response<ErrorResponse, WarehouseDto> addItem(WarehouseDto warehouseDto);
    Response<ErrorResponse,WarehouseDto> updateItem(String id, WarehouseDto warehouseDto);
    Response<ErrorResponse,WarehouseDto> deleteItemById(String id);
    Response<ErrorResponse,WarehouseDto> decreaseProductCountInWarehouse(List<OrderProductCountDto> orderProductCountDtos);
    Response<ErrorResponse,List<WarehouseDto>> getAll(int pageNumber,int pageSize,String sortedField,String direction,String deleted);
    Response<ErrorResponse,List<WarehouseDto>> getProductByName(int pageNumber,int pageSize,String sortedField,String direction,String name);
}
