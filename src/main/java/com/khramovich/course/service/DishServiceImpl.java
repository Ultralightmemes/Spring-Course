package com.khramovich.course.service;

import com.khramovich.course.repository.DishDao;
import com.khramovich.course.models.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService{

    @Autowired
    private DishDao dishDao;

    @Override
    public void save(Dish dish) {
        dishDao.save(dish);
    }

    @Override
    public List<Dish> findAll() {
        return dishDao.findAll();
    }

    @Override
    public Dish findById(Long id) {
        return dishDao.getById(id);
    }
}
