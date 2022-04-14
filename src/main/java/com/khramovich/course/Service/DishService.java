package com.khramovich.course.Service;

import com.khramovich.course.Models.Dish;

import java.util.List;

public interface DishService {
    void save(Dish dish);

    List<Dish> findAll();
}
