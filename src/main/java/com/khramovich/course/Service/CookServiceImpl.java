package com.khramovich.course.Service;

import com.khramovich.course.DAO.CookDao;
import com.khramovich.course.DAO.RoleDao;
import com.khramovich.course.Models.Cook;
import com.khramovich.course.Models.Role;
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
}
