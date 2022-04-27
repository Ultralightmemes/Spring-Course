package com.khramovich.course.Controller;

import com.khramovich.course.service.Dish_setService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sets")
public class SetController {

    @Autowired
    private Dish_setService dish_setService;

    @GetMapping("/show")
    public String show(){
        return "set/show";
    }
}
