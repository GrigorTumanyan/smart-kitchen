package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.order.AddOrderDto;
import com.epam.smartkitchen.dto.order.DeleteOrderDto;
import com.epam.smartkitchen.dto.order.OrderDto;
import com.epam.smartkitchen.dto.order.UpdateOrderDto;
import com.epam.smartkitchen.enums.OrderState;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrder(Pageable pageable, String deleted);

    OrderDto findById(String id);

    List<OrderDto> getOrdersByState(OrderState orderState, Pageable pageable, String deleted);

    OrderDto addOrder(AddOrderDto addOrderDto);

    OrderDto updateOrder(String id, UpdateOrderDto orderUpdate);

    DeleteOrderDto deleteOrder(String id);
}
