package com.khramovich.course.Controller;

import com.khramovich.course.service.DishService;
import com.khramovich.course.service.Dish_setService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sets")
public class SetController {

    @Autowired
    private Dish_setService dish_setService;

    @Autowired
    private DishService dishService;

    @GetMapping("/show")
    public String show(Model model){
        model.addAttribute("sets", dish_setService.findAll());
        return "set/show_all";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        model.addAttribute("set", dish_setService.getById(id));
        return "set/show";
    }
}
