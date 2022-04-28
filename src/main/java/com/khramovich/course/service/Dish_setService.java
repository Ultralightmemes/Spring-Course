package com.khramovich.course.service;

import com.khramovich.course.models.Dish_set;

import java.util.List;

public interface Dish_setService {
    void save(Dish_set dish_set);

    List<Dish_set> findAll();

    void deleteById(Long id);

    Dish_set getById(Long id);
}
