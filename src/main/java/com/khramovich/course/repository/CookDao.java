package com.khramovich.course.repository;

import com.khramovich.course.models.Cook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CookDao extends JpaRepository<Cook, Long> {
    Cook findByUsername(String username);
}
