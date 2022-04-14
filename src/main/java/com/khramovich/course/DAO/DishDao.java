package com.khramovich.course.DAO;

import com.khramovich.course.Models.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishDao extends JpaRepository<Dish, Long> {
}
