package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.mapper.WarehouseMapper;
import com.epam.smartkitchen.dto.user.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.warehouse.OrderProductCount;
import com.epam.smartkitchen.dto.warehouse.WarehouseDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.exceptions.RecordNotFoundException;
import com.epam.smartkitchen.models.Warehouse;
import com.epam.smartkitchen.repository.WarehouseRepository;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public Response<ErrorResponse, WarehouseDto> addItem(WarehouseDto warehouseRequestDto) {
        WarehouseDto warehouseDto = WarehouseMapper.warehouseToWarehouseDto(warehouseRepository.save(WarehouseMapper.warehouseDtoToWarehouse(warehouseRequestDto)));

        return new Response<>(null, warehouseDto, WarehouseDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, WarehouseDto> updateItem(String id, WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Item doesnt exist"));
        if (warehouse.getDeleted()) {
            throw new RecordNotFoundException("Item doesnt exist");
        }
        if (warehouseDto.getDescription() != null) {
            warehouse.setDescription(warehouseDto.getDescription());
        }
        if (warehouseDto.getPrice() != null) {
            warehouse.setPrice(warehouseDto.getPrice());
        }
        if (warehouseDto.getCount() != null) {
            warehouse.setCount(warehouseDto.getCount());
        }
        if (warehouseDto.getMeasurement() != null) {
            warehouse.setMeasurement(warehouseDto.getMeasurement());
        }
        if (warehouseDto.getProduct() != null) {
            warehouse.setProduct(warehouseDto.getProduct());
        }
        warehouseRepository.save(warehouse);
        WarehouseDto warehouseSaveDto = WarehouseMapper.warehouseToWarehouseDto(warehouseRepository.save(warehouse));


        return new Response<>(null, warehouseSaveDto, WarehouseDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, WarehouseDto> deleteItemById(String id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Item is not found with id : " + id));
        if (warehouse.getDeleted()) {
            throw new RecordNotFoundException("Item is not found with id");
        }
        warehouse.setDeleted(true);
        WarehouseDto warehouseDto = WarehouseMapper.warehouseToWarehouseDto(warehouseRepository.save(warehouse));
        return new Response<>(null,
                warehouseDto, ResponseDeleteUserDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, WarehouseDto> decreaseProductCountInWarehouse(List<OrderProductCount> orderProductCounts) {
        WarehouseDto warehouseDto = null;
        for (OrderProductCount orderProductCount : orderProductCounts) {
            Warehouse warehouse = warehouseRepository.findById(orderProductCount.getWarehouseId()).orElseThrow(() -> new RecordNotFoundException("Product not found."));
            warehouse.setCount(warehouse.getCount() - orderProductCount.getCount());
            if (warehouse.getCount() - orderProductCount.getCount() < 0) {
                throw new RuntimeException("We don't have that many products");
            }
            Warehouse save = warehouseRepository.save(warehouse);
            warehouseDto = WarehouseMapper.warehouseToWarehouseDto(save);

        }
        return new Response<>(null, warehouseDto, WarehouseDto.class.getSimpleName());

    }


}

