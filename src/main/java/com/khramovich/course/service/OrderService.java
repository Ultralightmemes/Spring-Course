package com.khramovich.course.service;

import com.khramovich.course.models.Order;

import java.util.List;

public interface OrderService {
    void save(Order order);

    List<Order> findAll();

    void deleteById(Long id);

    Order getById(Long id);
}
