package com.khramovich.course.Validator;

import com.khramovich.course.Models.Cook;
import com.khramovich.course.Service.CookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CookValidator implements Validator {

    @Autowired
    private CookService cookService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Cook.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Cook cook = (Cook) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        if (cook.getUsername().length() < 8 || cook.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }

        if (cookService.findByUsername(cook.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (cook.getUsername().length() < 8 || cook.getUsername().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!cook.getConfirmPassword().equals(cook.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }
}
