package com.epam.smartkitchen.dto.order;

import java.util.Objects;

public class OrderMenuItemDto {

    private String menuItemId;
    private Integer count;

    public OrderMenuItemDto() {
    }

    public OrderMenuItemDto(String id, Integer count) {
        this.menuItemId = id;
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
        OrderMenuItemDto that = (OrderMenuItemDto) o;
        return Objects.equals(menuItemId, that.menuItemId) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuItemId, count);
    }

    @Override
    public String toString() {
        return "OrderMenuItemDto{" + "id='" + menuItemId + '\'' + ", count=" + count + '}';
    }
}
