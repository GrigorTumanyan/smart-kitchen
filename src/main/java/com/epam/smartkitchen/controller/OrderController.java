package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.order.AddOrderDto;
import com.epam.smartkitchen.dto.order.DeleteOrderDto;
import com.epam.smartkitchen.dto.order.OrderDto;
import com.epam.smartkitchen.dto.order.UpdateOrderDto;
import com.epam.smartkitchen.enums.OrderState;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<Response<ErrorResponse, List<OrderDto>>> getOrders(@RequestParam(required = false) int pageNumber,
                                                                             @RequestParam(required = false) int pageSize,
                                                                             @RequestParam(required = false) String deleted,
                                                                             @RequestParam(required = false) String field,
                                                                             @RequestParam(required = false) String direction) {
        Response<ErrorResponse, List<OrderDto>> allOrders = orderService.getAllOrder(pageNumber, pageSize, deleted, field, direction);
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ErrorResponse, OrderDto>> getOrderById(@PathVariable(name = "id") String id) {
        Response<ErrorResponse, OrderDto> orderDto = orderService.findById(id);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/{orderState}")
    public ResponseEntity<Response<ErrorResponse, List<OrderDto>>> getOrderByState(@PathVariable OrderState orderState,
                                                                                   @RequestParam(required = false) int pageNumber,
                                                                                   @RequestParam(required = false) int pageSize,
                                                                                   @RequestParam(required = false) String deleted,
                                                                                   @RequestParam(required = false) String direction,
                                                                                   @RequestParam(required = false) String field) {
        Response<ErrorResponse, List<OrderDto>> ordersByState = orderService.getOrdersByState(orderState, pageNumber, pageSize, field, direction, deleted);
        return ResponseEntity.ok(ordersByState);
    }

    @PostMapping("/")
    public ResponseEntity<Response<ErrorResponse, OrderDto>> addOrder(@Valid @RequestBody AddOrderDto addOrderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult
                    .getFieldErrors()
                    .forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Response<ErrorResponse, OrderDto> orderDto = orderService.addOrder(addOrderDto);
        return ResponseEntity.ok(orderDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response<ErrorResponse, OrderDto>> updateOrder(@Valid @PathVariable(name = "id") String id, @RequestBody UpdateOrderDto updateOrderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult
                    .getFieldErrors()
                    .forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Response<ErrorResponse, OrderDto> orderDto = orderService.updateOrder(id, updateOrderDto);
        return ResponseEntity.ok(orderDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<ErrorResponse, DeleteOrderDto>> delete(@PathVariable(name = "id") String id) {
        Response<ErrorResponse, DeleteOrderDto> deletedOrderDto = orderService.deleteOrder(id);
        return ResponseEntity.ok(deletedOrderDto);
    }

    @PatchMapping("/cancel/{id}")
    public ResponseEntity<Response<ErrorResponse, OrderDto>> cancel(@PathVariable(name = "id") String id) {
        Response<ErrorResponse, OrderDto> canceledOrderDto = orderService.cancelOrder(id);
        return ResponseEntity.ok(canceledOrderDto);
    }
}
