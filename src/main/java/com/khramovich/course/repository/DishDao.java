package com.khramovich.course.repository;

import com.khramovich.course.models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishDao extends JpaRepository<Dish, Long> {
}
