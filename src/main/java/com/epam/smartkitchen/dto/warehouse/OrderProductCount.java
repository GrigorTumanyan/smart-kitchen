package com.epam.smartkitchen.dto.warehouse;

import com.epam.smartkitchen.models.Warehouse;

public class OrderProductCount {
    private String warehouseId;
    private Double count;

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }
}
