package com.khramovich.course.service;

import com.khramovich.course.models.Cook;

import java.util.List;

public interface CookService {
    void save(Cook cook);

    void update(Cook cook);

    Cook findByUsername(String username);

    List<Cook> findAll();

    void deleteById(Long id);

    Cook getById(Long id);
}
