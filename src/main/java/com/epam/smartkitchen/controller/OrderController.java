package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.order.AddOrderDto;
import com.epam.smartkitchen.dto.order.DeleteOrderDto;
import com.epam.smartkitchen.dto.order.OrderDto;
import com.epam.smartkitchen.enums.OrderState;
import com.epam.smartkitchen.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> getOrders(@RequestParam int pageNo,
                                                    @RequestParam int pageSize,
                                                    @RequestParam(required = false) String deleted,
                                                    @RequestParam(required = false) String field,
                                                    @RequestParam(required = false) String direction) {
        List<OrderDto> allOrders = orderService.getAllOrder(createPageable(pageNo, pageSize, field, direction), deleted);
        if (allOrders.isEmpty()) {
            return ResponseEntity.notFound().eTag("Order not found").build();
        }
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable(name = "id") String id) {

        OrderDto orderDto = orderService.findById(id);
        if (orderDto == null) {
            return ResponseEntity.notFound().eTag(id + " not found").build();
        }
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/{orderState}")
    public ResponseEntity<List<OrderDto>> getOrderByState(@PathVariable OrderState orderState,
                                                          @RequestParam int pageNo,
                                                          @RequestParam int pageSize,
                                                          @RequestParam(required = false) String deleted,
                                                          @RequestParam(required = false) String direction,
                                                          @RequestParam(required = false) String field) {
        List<OrderDto> ordersByState = orderService.getOrdersByState(orderState, createPageable(pageNo, pageSize, field, direction), deleted);
        if (ordersByState == null) {
            return ResponseEntity.notFound().eTag(orderState + " is not found").build();
        }
        return ResponseEntity.ok(ordersByState);
    }

    @PostMapping("/")
    public ResponseEntity<OrderDto> addOrder(@RequestBody AddOrderDto addOrderDto) {
        OrderDto savedOrderDto = orderService.addOrder(addOrderDto);
        if (savedOrderDto == null) {
            return ResponseEntity.notFound().eTag("Order is not created").build();
        }
        return ResponseEntity.ok(savedOrderDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable(name = "id") String id,
                                                @RequestBody OrderDto updateOrderDto) {
        OrderDto orderDto = orderService.updateOrder(id, updateOrderDto);
        if (orderDto == null) {
            return ResponseEntity.notFound().eTag(id + " is not exist").build();
        }
        return ResponseEntity.ok(orderDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteOrderDto> delete(@PathVariable(name = "id") String id){
        DeleteOrderDto deleteOrderDto = orderService.deleteOrder(id);
        if (deleteOrderDto == null){
            return ResponseEntity.notFound().eTag(id + " is not exist").build();
        }
        return ResponseEntity.ok(deleteOrderDto);
    }

    private PageRequest createPageable(int pageNo, int pageSize, String field, String direction) {
        if (field == null) {
            return PageRequest.of(pageNo, pageSize);
        } else if (direction == null) {
            return PageRequest.of(pageNo, pageSize).withSort(Sort.by(field).ascending());
        } else {
            return PageRequest.of(pageNo, pageSize).withSort(Sort.by(field).descending());
        }
    }
}
