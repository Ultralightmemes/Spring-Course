package com.khramovich.course.Controller;

import com.khramovich.course.repository.CookDao;
import com.khramovich.course.repository.DishDao;
import com.khramovich.course.repository.Dish_setDao;
import com.khramovich.course.models.Dish;
import com.khramovich.course.models.Dish_set;
import com.khramovich.course.service.CookService;
import com.khramovich.course.service.DishService;
import com.khramovich.course.service.Dish_setService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/dishes")
public class DIshController {

    @Autowired
    DishService dishService;

    @Autowired
    CookService cookService;

    @Autowired
    Dish_setService dish_setService;

    @GetMapping("/add")
    public String addDish(Model model) {
        return "dish/add";
    }

    @PostMapping("/add")
    public String addDish(@ModelAttribute("addForm") Dish dish, @ModelAttribute("file") MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        dish.setCook(cookService.findByUsername(authentication.getName()));
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                dish.setImage(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dishService.save(dish);
        return "redirect:/";
    }

    @GetMapping("/show")
    public String showDishes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", authentication.getName());
        model.addAttribute("dishes", dishService.findAll());
        return "dish/show";
    }

    @GetMapping("/show/{id}")
    public String showDish(@PathVariable("id") Long dishId, Model model) {
        model.addAttribute("dish", dishService.findById(dishId));
        return "dish/dish";
    }

    @PostMapping("/delete/{id}")
    public String deleteDish(@PathVariable("id") Long id, HttpServletRequest request) {
        dishService.deleteById(id);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/update/{id}")
    public String updateDish(@PathVariable("id") Long id, Model model) {
        model.addAttribute("dish", dishService.findById(id));
        return "dish/update";
    }

    @PostMapping("/update/{id}")
    public String updateDish(@PathVariable("id") Long id, @ModelAttribute("updateForm") Dish dish,
                             @ModelAttribute("file") MultipartFile file){
        Dish oldDish = dishService.findById(id);
        oldDish.setCountry(dish.getCountry());
        oldDish.setDescription(dish.getDescription());
        oldDish.setIngredients(dish.getIngredients());
        oldDish.setName(dish.getName());
        oldDish.setPrice(dish.getPrice());
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                oldDish.setImage(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dishService.save(oldDish);
        return "redirect:../show";
    }
}
