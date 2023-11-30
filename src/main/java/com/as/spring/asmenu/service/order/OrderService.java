package com.as.spring.asmenu.service.order;

import com.as.spring.asmenu.model.Order;

import java.util.List;

public interface OrderService {
    void save(Order order);

    List<Order> findAll();

    Order findById(Long orderId);

    void orderIsDelivered(Long orderId);
}
