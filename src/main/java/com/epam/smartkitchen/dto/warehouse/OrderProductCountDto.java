package com.epam.smartkitchen.dto.warehouse;

public class OrderProductCountDto {
    private String productId;
    private Double count;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String warehouseId) {
        this.productId = warehouseId;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }
}
