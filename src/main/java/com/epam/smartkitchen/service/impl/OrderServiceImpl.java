package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.mapper.OrderMapper;
import com.epam.smartkitchen.dto.order.AddOrderDto;
import com.epam.smartkitchen.dto.order.DeleteOrderDto;
import com.epam.smartkitchen.dto.order.OrderDto;
import com.epam.smartkitchen.dto.order.UpdateOrderDto;
import com.epam.smartkitchen.enums.OrderState;
import com.epam.smartkitchen.exceptions.ConflictException;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.exceptions.RecordNotFoundException;
import com.epam.smartkitchen.exceptions.RequestParamInvalidException;
import com.epam.smartkitchen.models.MenuItem;
import com.epam.smartkitchen.models.Order;
import com.epam.smartkitchen.repository.OrderRepository;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Response<ErrorResponse, List<OrderDto>> getAllOrder(int pageNumber, int pageSize, String sortedField, String direction, String deleted) {
        PageRequest pageable = createPageable(pageNumber, pageSize, sortedField, direction);
        Page<Order> allOrders = null;

        if (deleted == null) {
            allOrders = orderRepository.findAllByDeletedFalse(pageable);
        } else if (deleted.equals("one")) {
            allOrders = orderRepository.findAllByDeletedTrue(pageable);
        } else if (deleted.equals("all")) {
            orderRepository.findAll(pageable);
        } else {
            throw new RequestParamInvalidException("Parameter deleted is not correct: " + deleted);
        }
        if (allOrders.getContent().size() < 1) {
            throw new RecordNotFoundException("Orders are not found");
        } else {
            return new Response<>(null, toOrderDto(allOrders), OrderDto.class.getName());
        }
    }

    @Override
    public Response<ErrorResponse, OrderDto> findById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order is not found" + id));
        return new Response<>(null, new OrderDto(order), OrderDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, List<OrderDto>> getOrdersByState(OrderState orderState, int pageNumber, int pageSize, String sortedField, String direction, String deleted) {
        PageRequest pageable = createPageable(pageNumber, pageSize, sortedField, direction);
        Page<Order> allOrders = null;
        if (deleted == null) {
            allOrders = orderRepository.findByStateAndDeletedFalse(orderState, pageable);
        } else if (deleted.equals("one")) {
            allOrders = orderRepository.findByStateAndDeletedTrue(orderState, pageable);
        } else if (deleted.equals("all")) {
            orderRepository.findOrderByState(orderState, pageable);
        } else {
            throw new RequestParamInvalidException("Parameter deleted is not correct: " + deleted);
        }
        if (allOrders.getContent().size() < 1) {
            throw new RecordNotFoundException("Orders type are not found" + deleted);
        }
        return new Response<>(null, toOrderDto(allOrders), OrderDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, OrderDto> addOrder(AddOrderDto addOrderDto) {
        Order order = OrderMapper.addOrderDtoToOrder(addOrderDto);
        List<MenuItem> itemList = order.getItemsList();
        double price = 0;
        for (MenuItem menuItem : itemList) {
            decreaseMenuItem(menuItem.getName(), menuItem.getWeight(), menuItem.getmeasurement());
            price = +menuItem.getPrice();
        }
        order.setTotalPrice(price);
        Order savedOrder = orderRepository.save(order);

        return new Response<>(null, new OrderDto(savedOrder), OrderDto.class.getSimpleName());
    }

    private void decreaseMenuItem(String name, Double weight, String measurement) {
        new MenuItem(name, null, weight, null, measurement, null);
    }

    @Override
    public Response<ErrorResponse, OrderDto> updateOrder(String id, UpdateOrderDto orderUpdate) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new RecordNotFoundException(id + " id not found!");
        }
        Optional<?> mapOrder = order.map(orders -> {
            orders.setState(orderUpdate.getOrderState());
            orders.setItemsList(orderUpdate.getItemList());

            List<MenuItem> itemsList = orders.getItemsList();
            Double price = null;
            for (MenuItem menuItem : itemsList) {
                price = +menuItem.getPrice();
            }
            Double percent = price / 10;
            Double totalAmount = price + percent;

            orders.setTotalPrice(totalAmount);

            return orderUpdate;
        });
        Order toOrder = OrderMapper.updateOrderDtoToOrder((UpdateOrderDto) mapOrder.get());
        Order save = orderRepository.save(toOrder);
        return new Response<>(null, new OrderDto(save), OrderDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, DeleteOrderDto> deleteOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order is not found" + id));
        order.setDeleted(true);
        Order saveOrder = orderRepository.save(order);
        return new Response<>(null, new DeleteOrderDto(saveOrder.getDeleted()), DeleteOrderDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, OrderDto> cancelOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order is not found" + id));
        if (order.getState().equals(OrderState.PENDING)) {
            throw new ConflictException("There is conflict this request");
        }
        order.setState(OrderState.CANCELED);
        orderRepository.save(order);

        return new Response<>(null, new OrderDto(order), OrderDto.class.getSimpleName());
    }

    private PageRequest createPageable(int pageNumber, int pageSize, String field, String direction) {
        if (field == null) {
            return PageRequest.of(pageNumber, pageSize);
        } else if (direction == null) {
            return PageRequest.of(pageNumber, pageSize).withSort(Sort.by(field).ascending());
        } else {
            return PageRequest.of(pageNumber, pageSize).withSort(Sort.by(field).descending());
        }
    }

    private List<OrderDto> toOrderDto(Page<Order> orderList) {
        List<OrderDto> allOrderDto = new ArrayList<>();
        for (Order order : orderList) {
            OrderDto orderDto = new OrderDto(order);
            allOrderDto.add(orderDto);
        }
        return allOrderDto;
    }
}
