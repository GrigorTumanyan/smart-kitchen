package com.epam.smartkitchen.models;

import javax.persistence.*;
import java.util.Objects;


@Entity
public class OrderMenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    @Column(name = "menu_item_id")
    private String menuItemId;
    @Column
    private Integer count;

    public OrderMenuItem() {
    }

    public OrderMenuItem(String menuItemId, Integer count) {
        this.menuItemId = menuItemId;
        this.count = count;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderMenuItem that = (OrderMenuItem) o;
        return Objects.equals(menuItemId, that.menuItemId) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuItemId, count);
    }

    @Override
    public String toString() {
        return "OrderMenuItem{" + "menuItemId='" + menuItemId + '\'' + ", count=" + count + '}';
    }
}
