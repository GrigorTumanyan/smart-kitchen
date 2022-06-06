package com.epam.smartkitchen.dto.mapper;

import com.epam.smartkitchen.dto.order.OrderDto;
import com.epam.smartkitchen.dto.order.OrderMenuItemDto;
import com.epam.smartkitchen.dto.order.UpdateOrderDto;
import com.epam.smartkitchen.models.Order;
import com.epam.smartkitchen.models.OrderMenuItem;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static Order dtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setWaiter(orderDto.getWaiter());
        order.setCook(orderDto.getCook());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setState(orderDto.getOrderState());
        List<OrderMenuItem> orderMenuItem = new ArrayList<>();
        order.setOrderMenuItems(orderMenuItem);

        return order;
    }

    public static OrderDto orderToDto(Order order) {
        OrderDto orderDto = new OrderDto(order);
        orderDto.setWaiter(order.getWaiter());
        orderDto.setCook(order.getCook());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setOrderState(order.getState());
        List<OrderMenuItemDto> orderMenuItemDtoList = new ArrayList<>();
        orderDto.setOrderMenuItemDto(orderMenuItemDtoList);

        return orderDto;
    }

    public static Order updateOrderDtoToOrder(UpdateOrderDto updateOrderDto) {
        Order order = new Order();
        order.setState(updateOrderDto.getOrderState());
        List<OrderMenuItem> itemList = new ArrayList<>();
        order.setOrderMenuItems(itemList);

        return order;
    }

    public static OrderMenuItem orderMenuItemDtoToOrderMenuItem(OrderMenuItemDto orderMenuItemDto) {
        OrderMenuItem orderMenuItem = new OrderMenuItem();
        orderMenuItem.setMenuItemId(orderMenuItemDto.getMenuItemId());
        orderMenuItem.setCount(orderMenuItemDto.getCount());

        return orderMenuItem;
    }

    public static OrderMenuItemDto orderMenuItemToOrderMenuItemDto(OrderMenuItem orderMenuItem) {
        OrderMenuItemDto orderMenuItemDto = new OrderMenuItemDto();
        orderMenuItemDto.setMenuItemId(orderMenuItem.getMenuItemId());
        orderMenuItemDto.setCount(orderMenuItem.getCount());

        return orderMenuItemDto;
    }
}
