//package com.epam.smartkitchen.service.impl;
//
//import com.epam.smartkitchen.dto.mapper.OrderMapper;
//import com.epam.smartkitchen.dto.order.DeleteOrderDto;
//import com.epam.smartkitchen.dto.order.OrderDto;
//import com.epam.smartkitchen.enums.OrderState;
//import com.epam.smartkitchen.models.MenuItem;
//import com.epam.smartkitchen.models.Order;
//import com.epam.smartkitchen.models.OrderMenuItem;
//import com.epam.smartkitchen.models.User;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class TestHelperOrder {
//
//    protected static Optional<Order> toOptionalOrder() {
//        List<OrderMenuItem> menuItemList = new ArrayList<>();
//        Order order = new Order();
//        order.setWaiter(new User());
//        order.setCook(new User());
//        order.setState(OrderState.ACCEPTED);
//        order.setTotalPrice(1000D);
//        order.setOrderMenuItems(menuItemList);
//        order.setDeleted(true);
//        return Optional.of(order);
//    }
//
//    protected static OrderDto toOrderDto() {
//        Order order = toOptionalOrder().get();
//        OrderDto orderDto = OrderMapper.orderToDto(order);
//        return orderDto;
//    }
//
//    protected static Order toOrder() {
//        Order order = toOptionalOrder().get();
//        return order;
//    }
//
//    protected static Page<Order> orderPageable() {
//        List<OrderMenuItem> menuItemList = new ArrayList<>();
//        List<Order> orderList = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            Order order = new Order();
//            order.setWaiter(new User());
//            order.setCook(new User());
//            order.setTotalPrice(i + 1000.0);
//            order.setState(OrderState.ACCEPTED);
//            order.setOrderMenuItems(menuItemList);
//            orderList.add(order);
//        }
//        return new PageImpl<>(orderList);
//
//    }
//
//    protected static OrderDto toOrderDtoFromOptionalOrder() {
//        return new OrderDto(toOrder());
//    }
//
//    protected static DeleteOrderDto deleteOrderDto() {
//        Order order = toOrder();
//        return new DeleteOrderDto(order.getDeleted());
//    }
//
//    protected static Page<OrderDto> getOrderDto(Page<Order> orders) {
//        List<OrderDto> orderDtoList = new ArrayList<>();
//        for (Order order : orders) {
//            orderDtoList.add(new OrderDto(order));
//        }
//        return new PageImpl<>(orderDtoList);
//    }
//}
