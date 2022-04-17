package com.khramovich.course.Controller;

import com.khramovich.course.DAO.CookDao;
import com.khramovich.course.DAO.DishDao;
import com.khramovich.course.Models.Dish;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Controller
@RequestMapping("/dishes")
public class DIshController {

    @Autowired
    DishDao dishDao;

    @Autowired
    CookDao cookDao;

    @GetMapping("/add")
    public String addDish(Model model){
        return "dish/add";
    }

    @PostMapping("/add")
    public String addDish(@ModelAttribute("addForm") Dish dish, @ModelAttribute("file") MultipartFile file){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        dish.setCook(cookDao.findByUsername(authentication.getName()));
        if (!file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                dish.setImage(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dishDao.save(dish);
        return "redirect:/";
    }

    @GetMapping("/show")
    public String showDishes(Model model){
        model.addAttribute("dishes", dishDao.findAll());
        return "dish/show";
    }

    @GetMapping("/show/{id}")
    public String showDish(@PathVariable("id") Long dishId, Model model){
        model.addAttribute("dish", dishDao.getById(dishId));
        return "dish/dish";
    }
}
