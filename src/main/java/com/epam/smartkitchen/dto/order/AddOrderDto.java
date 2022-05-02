package com.epam.smartkitchen.dto.order;

import com.epam.smartkitchen.enums.OrderState;
import com.epam.smartkitchen.models.MenuItem;
import com.epam.smartkitchen.models.User;

import java.util.List;
import java.util.Objects;

public class AddOrderDto {

    User waiter;

    OrderState orderState;

    List<MenuItem> menuItemList;

    public AddOrderDto() {
    }

    public AddOrderDto(User waiter, OrderState orderState, List<MenuItem> menuItemList) {
        this.waiter = waiter;
        this.orderState = orderState;
        this.menuItemList = menuItemList;
    }

    public User getWaiter() {
        return waiter;
    }

    public void setWaiter(User waiter) {
        this.waiter = waiter;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public List<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddOrderDto that = (AddOrderDto) o;
        return Objects.equals(waiter, that.waiter) && orderState == that.orderState && Objects.equals(menuItemList, that.menuItemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(waiter, orderState, menuItemList);
    }

    @Override
    public String toString() {
        return "AddOrderDto{" +
                "waiter=" + waiter +
                ", orderState=" + orderState +
                ", menuItemList=" + menuItemList +
                '}';
    }
}
