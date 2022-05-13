package com.khramovich.course.service;

import com.khramovich.course.models.Order;
import com.khramovich.course.repository.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public void deleteById(Long id) {
        orderDao.deleteById(id);
    }

    @Override
    public Order getById(Long id) {
        return orderDao.getById(id);
    }
}
