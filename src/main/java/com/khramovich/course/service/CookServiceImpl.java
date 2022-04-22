package com.khramovich.course.service;

import com.khramovich.course.repository.CookDao;
import com.khramovich.course.repository.RoleDao;
import com.khramovich.course.models.Cook;
import com.khramovich.course.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CookServiceImpl implements CookService{

    @Autowired
    private CookDao cookDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Cook cook) {
        cook.setPassword(bCryptPasswordEncoder.encode(cook.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getById(1L));
        cook.setRoles(roles);
        cookDao.save(cook);
    }

    @Override
    public Cook findByUsername(String username) {
        return cookDao.findByUsername(username);
    }

    @Override
    public void update(Cook cook) {
        cookDao.save(cook);
    }
}
