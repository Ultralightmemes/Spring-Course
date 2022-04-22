package com.khramovich.course.service;

import com.khramovich.course.models.Cook;

public interface CookService {
    void save(Cook cook);

    void update(Cook cook);

    Cook findByUsername(String username);
}
