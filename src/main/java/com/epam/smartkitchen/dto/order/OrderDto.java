package com.epam.smartkitchen.dto.order;

import com.epam.smartkitchen.enums.OrderState;
import com.epam.smartkitchen.models.Order;
import com.epam.smartkitchen.models.User;

import java.util.List;
import java.util.Objects;

public class OrderDto {

    private User waiter;

    private User cook;

    private OrderState orderState;

    private Double totalPrice;

    List<OrderMenuItemDto> orderMenuItemDto;

    public OrderDto(Order order) {
    }

    public OrderDto(User waiter, User cook, OrderState orderState, Double totalPrice, List<OrderMenuItemDto> orderMenuItemDto) {
        this.waiter = waiter;
        this.cook = cook;
        this.orderState = orderState;
        this.totalPrice = totalPrice;
        this.orderMenuItemDto = orderMenuItemDto;
    }

    public User getWaiter() {
        return waiter;
    }

    public void setWaiter(User waiter) {
        this.waiter = waiter;
    }

    public User getCook() {
        return cook;
    }

    public void setCook(User cook) {
        this.cook = cook;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderMenuItemDto> getOrderMenuItemDto() {
        return orderMenuItemDto;
    }

    public void setOrderMenuItemDto(List<OrderMenuItemDto> orderMenuItemDto) {
        this.orderMenuItemDto = orderMenuItemDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(waiter, orderDto.waiter) && Objects.equals(cook, orderDto.cook) && orderState == orderDto.orderState && Objects.equals(totalPrice, orderDto.totalPrice) && Objects.equals(orderMenuItemDto, orderDto.orderMenuItemDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(waiter, cook, orderState, totalPrice, orderMenuItemDto);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "waiter=" + waiter +
                ", cook=" + cook +
                ", orderState=" + orderState +
                ", totalPrice=" + totalPrice +
                ", orderMenuItemDto=" + orderMenuItemDto +
                '}';
    }
}
