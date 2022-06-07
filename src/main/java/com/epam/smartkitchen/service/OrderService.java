package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.order.DeleteOrderDto;
import com.epam.smartkitchen.dto.order.OrderDto;
import com.epam.smartkitchen.dto.order.UpdateOrderDto;
import com.epam.smartkitchen.enums.OrderState;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;

import java.util.List;

public interface OrderService {

    Response<ErrorResponse, List<OrderDto>> getAllOrder(int pageNumber, int pageSize, String sortedField, String direction, String deleted);

    Response<ErrorResponse, OrderDto> findById(String id);

    Response<ErrorResponse, List<OrderDto>> getOrdersByState(OrderState orderState, int pageNumber, int pageSize, String sortedField, String direction, String deleted);

    Response<ErrorResponse, OrderDto> addOrder(OrderDto orderDto);

    Response<ErrorResponse, OrderDto> updateOrder(String id, UpdateOrderDto orderUpdate);

    Response<ErrorResponse, DeleteOrderDto> deleteOrder(String id);

    Response<ErrorResponse, OrderDto> cancelOrder(String id);
}
