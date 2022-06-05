package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.order.DeleteOrderDto;
import com.epam.smartkitchen.dto.order.OrderDto;
import com.epam.smartkitchen.enums.OrderState;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.exceptions.RecordNotFoundException;
import com.epam.smartkitchen.models.Order;
import com.epam.smartkitchen.repository.MenuRepository;
import com.epam.smartkitchen.repository.OrderRepository;
import com.epam.smartkitchen.response.Response;
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

import java.util.List;
import java.util.Optional;

import static com.epam.smartkitchen.service.impl.TestHelperOrder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {

    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private final MenuRepository menuRepository = Mockito.mock(MenuRepository.class);
    @InjectMocks
    private final OrderService orderService = Mockito.spy(new OrderServiceImpl(orderRepository, menuRepository));

    private String id;

    private PageRequest pageRequest;

    private Optional<Order> optionalOrder;

    OrderDto orderDto;

    private Order order;

    private DeleteOrderDto deleteOrderDto;

    @BeforeEach
    void setUp() {
        pageRequest = PageRequest.of(0, 10);
        id = "111";
        optionalOrder = toOptionalOrder();
        orderDto = toOrderDtoFromOptionalOrder();
        order = toOrder();
        deleteOrderDto = deleteOrderDto();
    }


    @Test
    void findById() {
        when(orderRepository.findById(id)).thenReturn(optionalOrder);

        Response<ErrorResponse, OrderDto> orderById = orderService.findById(id);

        assertEquals(orderById.getSuccessObject(), orderDto);
    }

    @Test
    void negativeCaseFindById() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(RecordNotFoundException.class, () -> orderService.findById(id));
    }

    @Test
    void getOrders() {
        when(orderRepository.findAll(pageRequest)).thenReturn((orderPageable()));
        when(orderRepository.findAllByDeletedFalse(pageRequest)).thenReturn(orderPageable());
        when(orderRepository.findAllByDeletedTrue(pageRequest)).thenReturn(orderPageable());

        Response<ErrorResponse, List<OrderDto>> orderDtoList = orderService.getAllOrder(0, 10, null, null, null);
        Page<OrderDto> orderDtoPage = new PageImpl<>(orderDtoList.getSuccessObject());

        assertEquals(orderDtoPage, getOrderDto(orderPageable()));
    }

    @Test
    void getOrderByType() {
        when(orderRepository.findOrderByState(OrderState.ACCEPTED, pageRequest)).thenReturn(orderPageable());
        when(orderRepository.findByStateAndDeletedFalse(OrderState.ACCEPTED, pageRequest)).thenReturn(orderPageable());
        when(orderRepository.findByStateAndDeletedTrue(OrderState.ACCEPTED, pageRequest)).thenReturn(orderPageable());

        Response<ErrorResponse, List<OrderDto>> orderDtoList = orderService.getOrdersByState(OrderState.ACCEPTED, 0, 10, null, null, null);
        Page<OrderDto> orderDtoPage = new PageImpl<>(orderDtoList.getSuccessObject());

        assertEquals(orderDtoPage, getOrderDto(orderPageable()));
    }

    @Test
    void deleteOrder() {
        when(orderRepository.findById(id)).thenReturn(toOptionalOrder());
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Response<ErrorResponse, DeleteOrderDto> deleteOrderDtoResponse = orderService.deleteOrder(id);

        assertEquals(deleteOrderDtoResponse.getSuccessObject().isDeleted(), deleteOrderDto.isDeleted());
    }

    @Test
    void negativeCaseDeleteOrder() {
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> orderService.deleteOrder(id));
    }
}