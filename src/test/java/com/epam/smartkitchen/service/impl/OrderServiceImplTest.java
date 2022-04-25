package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.order.DeleteOrderDto;
import com.epam.smartkitchen.dto.order.OrderDto;
import com.epam.smartkitchen.enums.OrderState;
import com.epam.smartkitchen.models.MenuItem;
import com.epam.smartkitchen.models.Order;
import com.epam.smartkitchen.repository.OrderRepository;
import com.epam.smartkitchen.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.smartkitchen.service.impl.TestHelperOrder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {

    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);

    List<MenuItem> menuItemList = new ArrayList<>();

    @InjectMocks
    private final OrderService orderService = Mockito.spy(new OrderServiceImpl(orderRepository));

    private String id;

    private PageRequest pageRequest;

    private Optional<Order> optionalOrder;

    private OrderDto orderDto;

    private Order order;

    private DeleteOrderDto deleteOrderDto;

    @BeforeEach
    void setUp() {
        pageRequest = PageRequest.of(0, 10);
        id = "111";
        optionalOrder = toOptionalOrder();
        order = toOrder();
        deleteOrderDto = deleteOrderDto();
    }


    @Test
    void findById() {
        when(orderRepository.findById(id)).thenReturn(optionalOrder);

        OrderDto orderById = orderService.findById(id);

        assertEquals(orderById, toOrderDto());
    }

    @Test
    void negativeCaseFindById(){
        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> orderService.findById("111"));
    }

    @Test
    void getOrders() {
        when(orderRepository.findAll(pageRequest)).thenReturn((orderPageable()));
        when(orderRepository.findAllByDeletedFalse(pageRequest)).thenReturn(orderPageable());
        when(orderRepository.findAllByDeletedTrue(pageRequest)).thenReturn(orderPageable());

        List<OrderDto> orderDtoList = orderService.getAllOrder(pageRequest,null);
        Page<OrderDto> orderDtoPage= new PageImpl<>(orderDtoList);

        assertEquals(orderDtoPage, getOrderDto(orderPageable()));
    }

    @Test
    void getOrderByType() {
        when(orderRepository.findOrderByState(OrderState.ACCEPTED, pageRequest)).thenReturn(orderPageable());
        when(orderRepository.findByStateAndDeletedFalse(OrderState.ACCEPTED, pageRequest)).thenReturn(orderPageable());
        when(orderRepository.findByStateAndDeletedTrue(OrderState.ACCEPTED, pageRequest)).thenReturn(orderPageable());

        List<OrderDto> orderDtoList = orderService.getOrdersByState(OrderState.ACCEPTED,pageRequest,null);
        Page<OrderDto> orderDtoPage= new PageImpl<>(orderDtoList);

        assertEquals(orderDtoPage, getOrderDto(orderPageable()));
    }
}