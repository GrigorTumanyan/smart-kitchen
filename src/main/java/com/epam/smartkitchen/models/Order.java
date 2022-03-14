package com.epam.smartkitchen.models;

import com.epam.smartkitchen.enums.OrderState;

import javax.persistence.*;
import java.util.List;

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
    @ManyToMany
    List<MenuItem> ItemsList;

    public Order(User waiter, User cook, Double totalPrice, OrderState state, List<MenuItem> itemsList) {
        this.waiter = waiter;
        this.cook = cook;
        this.totalPrice = totalPrice;
        this.state = state;
        ItemsList = itemsList;
    }

    public Order() {
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

    public List<MenuItem> getItemsList() {
        return ItemsList;
    }

    public void setItemsList(List<MenuItem> itemsList) {
        ItemsList = itemsList;
    }
}
