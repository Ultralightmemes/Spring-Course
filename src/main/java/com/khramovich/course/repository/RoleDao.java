package com.khramovich.course.repository;

import com.khramovich.course.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
