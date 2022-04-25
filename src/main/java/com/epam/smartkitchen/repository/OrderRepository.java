package com.epam.smartkitchen.repository;

import com.epam.smartkitchen.enums.OrderState;
import com.epam.smartkitchen.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
    Page<Order> findOrderByState(OrderState orderState, Pageable pageable);

    Page<Order> findAll(Pageable pageable);

    Page<Order> findAllByDeletedFalse(Pageable pageable);

    Page<Order> findAllByDeletedTrue(Pageable pageable);

    Page<Order> findByStateAndDeletedTrue(OrderState orderState, Pageable pageable);

    Page<Order> findByStateAndDeletedFalse(OrderState orderState, Pageable pageable);
}
