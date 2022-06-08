package com.epam.smartkitchen.dto.order;

import com.epam.smartkitchen.enums.OrderState;
import com.epam.smartkitchen.models.OrderMenuItem;

import java.util.List;
import java.util.Objects;

public class UpdateOrderDto {

    private OrderState orderState;

    private List<OrderMenuItemDto> itemList;

    public UpdateOrderDto(OrderState orderState, List<OrderMenuItemDto> itemList) {
        this.orderState = orderState;
        this.itemList = itemList;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public List<OrderMenuItemDto> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderMenuItemDto> itemList) {
        this.itemList = itemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateOrderDto that = (UpdateOrderDto) o;
        return orderState == that.orderState && Objects.equals(itemList, that.itemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderState, itemList);
    }

    @Override
    public String toString() {
        return "UpdateOrderDto{" +
                "orderState=" + orderState +
                ", itemList=" + itemList +
                '}';
    }
}
