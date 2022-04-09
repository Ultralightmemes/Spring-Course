package com.khramovich.course.Service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
