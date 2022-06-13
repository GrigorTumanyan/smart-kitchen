package com.epam.smartkitchen.models;

import com.epam.smartkitchen.enums.OrderState;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "restaurant_order")
public class Order extends BaseEntity {
    @ManyToOne
    User waiter;
    @ManyToOne
    User cook;
    @Column(name = "total_price")
    Double totalPrice;
    @Column
    OrderState state;
    @OneToMany(mappedBy = "menuItemId")
    List<OrderMenuItem> orderMenuItems;

    public Order() {
    }

    public Order(User waiter, User cook, Double totalPrice, OrderState state, List<OrderMenuItem> orderMenuItems) {
        this.waiter = waiter;
        this.cook = cook;
        this.totalPrice = totalPrice;
        this.state = state;
        this.orderMenuItems = orderMenuItems;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public List<OrderMenuItem> getOrderMenuItems() {
        return orderMenuItems;
    }

    public void setOrderMenuItems(List<OrderMenuItem> orderMenuItems) {
        this.orderMenuItems = orderMenuItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(waiter, order.waiter) && Objects.equals(cook, order.cook) && Objects.equals(totalPrice, order.totalPrice) && state == order.state && Objects.equals(orderMenuItems, order.orderMenuItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), waiter, cook, totalPrice, state, orderMenuItems);
    }

    @Override
    public String toString() {
        return "Order{" + "waiter=" + waiter + ", cook=" + cook + ", totalPrice=" + totalPrice + ", state=" + state + ", orderMenuItems=" + orderMenuItems + '}';
    }
}
