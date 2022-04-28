package com.khramovich.course.service;

import com.khramovich.course.repository.Dish_setDao;
import com.khramovich.course.models.Dish_set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Dish_setServiceImpl implements Dish_setService{

    @Autowired
    private Dish_setDao dish_setDao;

    @Override
    public void save(Dish_set dish_set){
        dish_setDao.save(dish_set);
    }

    @Override
    public List<Dish_set> findAll(){
        return dish_setDao.findAll();
    }

    @Override
    public void deleteById(Long id){ dish_setDao.deleteById(id);}

    @Override
    public Dish_set getById(Long id) { return dish_setDao.getById(id);}
}
