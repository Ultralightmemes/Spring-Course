package com.khramovich.course.DAO;

import com.khramovich.course.Models.Cook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CookDao extends JpaRepository<Cook, Long> {
    Cook findByUsername(String username);
}
