package com.khramovich.course.repository;

import com.khramovich.course.models.Dish;
import com.khramovich.course.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Long> {
}
