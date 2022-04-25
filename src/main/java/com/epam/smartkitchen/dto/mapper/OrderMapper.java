package com.epam.smartkitchen.dto.mapper;

import com.epam.smartkitchen.dto.order.AddOrderDto;
import com.epam.smartkitchen.dto.order.OrderDto;
import com.epam.smartkitchen.models.Order;

public class OrderMapper {

    public static Order dtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setWaiter(orderDto.getWaiter());
        order.setCook(orderDto.getCook());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setState(orderDto.getOrderState());
        order.setItemsList(orderDto.getItemList());

        return order;
    }

    public static OrderDto orderToDto(Order order) {
        OrderDto orderDto = new OrderDto(order);
        orderDto.setWaiter(order.getWaiter());
        orderDto.setCook(order.getCook());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setOrderState(order.getState());
        orderDto.setItemList(order.getItemsList());

        return orderDto;
    }

    public static Order addOrderDtoToOrder(AddOrderDto addOrderDto) {
        Order order = new Order();
        order.setWaiter(addOrderDto.getWaiter());
        order.setState(addOrderDto.getOrderState());
        order.setItemsList(addOrderDto.getMenuItemList());

        return order;
    }
}
