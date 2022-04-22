package com.khramovich.course.service;

import com.khramovich.course.repository.CookDao;
import com.khramovich.course.models.Cook;
import com.khramovich.course.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CookDetailServiceImpl implements UserDetailsService {

    @Autowired
    private CookDao cookDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cook cook = cookDao.findByUsername(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role: cook.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(cook.getUsername(), cook.getPassword(), grantedAuthorities);
    }

}
