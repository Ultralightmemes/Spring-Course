package com.khramovich.course.service;

import com.khramovich.course.models.Dish;

import java.util.List;

public interface DishService {
    void save(Dish dish);

    List<Dish> findAll();

    Dish findById(Long id);
}
