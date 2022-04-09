package com.khramovich.course.Service;

import com.khramovich.course.Models.Cook;

public interface CookService {
    void save(Cook cook);

    Cook findByUsername(String username);
}
