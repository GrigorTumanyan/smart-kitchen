package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.mapper.OrderMapper;
import com.epam.smartkitchen.dto.order.AddOrderDto;
import com.epam.smartkitchen.dto.order.DeleteOrderDto;
import com.epam.smartkitchen.dto.order.OrderDto;
import com.epam.smartkitchen.dto.order.UpdateOrderDto;
import com.epam.smartkitchen.enums.OrderState;
import com.epam.smartkitchen.models.MenuItem;
import com.epam.smartkitchen.models.Order;
import com.epam.smartkitchen.repository.OrderRepository;
import com.epam.smartkitchen.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<OrderDto> getAllOrder(Pageable pageable, String deleted) {
        Page<Order> allOrders = orderRepository.findAll(pageable);

        if (deleted == null) {
            allOrders = orderRepository.findAllByDeletedFalse(pageable);
        } else if (deleted.equals("one")) {
            allOrders = orderRepository.findAllByDeletedTrue(pageable);
        } else if (deleted.equals("all")) {
            orderRepository.findAll(pageable);
        }
        if (allOrders == null) {
            return null;
        } else {
            return toOrderDto(allOrders);
        }
    }

    @Override
    public OrderDto findById(String id) {
        Optional<Order> orderId = orderRepository.findById(id);
        if (orderId.isEmpty()) {
            throw new RuntimeException(id + " id not found!");
        }
        Order order = orderId.get();
        OrderDto orderDto = OrderMapper.orderToDto(order);
        return orderDto;
    }

    @Override
    public List<OrderDto> getOrdersByState(OrderState orderState, Pageable pageable, String deleted) {
        Page<Order> stateOrder = orderRepository.findOrderByState(orderState, pageable);
        if (deleted == null) {
            stateOrder = orderRepository.findByStateAndDeletedFalse(orderState, pageable);
        } else if (deleted.equals("one")) {
            stateOrder = orderRepository.findByStateAndDeletedTrue(orderState, pageable);
        } else if (deleted.equals("all")) {
            orderRepository.findOrderByState(orderState, pageable);
        }
        if (stateOrder == null) {
            return null;
        }
        return toOrderDto(stateOrder);
    }

    @Override
    public OrderDto addOrder(AddOrderDto addOrderDto) {
        Order order = OrderMapper.addOrderDtoToOrder(addOrderDto);
        List<MenuItem> itemList = order.getItemsList();
        double price = 0;
        for (MenuItem menuItem : itemList) {
            decreaseMenuItem(menuItem.getName(), menuItem.getWeight(), menuItem.getmeasurement());
            price = +menuItem.getPrice();
        }
        order.setTotalPrice(price);
        Order savedOrder = orderRepository.save(order);
        return new OrderDto(savedOrder);
    }

    private void decreaseMenuItem(String name, Double weight, String measurement) {
        new MenuItem(name, null, weight, null, measurement, null);
    }

    @Override
    public OrderDto updateOrder(String id, UpdateOrderDto orderUpdate) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new RuntimeException(id + " id not found!");
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
        OrderDto orderDto = OrderMapper.orderToDto(save);
        return orderDto;
    }

    @Override
    public DeleteOrderDto deleteOrder(String id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }
        order.setDeleted(true);
        Order saveOrder = orderRepository.save(order);
        return new DeleteOrderDto(saveOrder.getDeleted());
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
