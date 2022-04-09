package com.khramovich.course.DAO;

import com.khramovich.course.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
