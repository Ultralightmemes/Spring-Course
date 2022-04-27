package com.khramovich.course.Controller;

import com.khramovich.course.models.Dish;
import com.khramovich.course.models.Dish_set;
import com.khramovich.course.repository.RoleDao;
import com.khramovich.course.service.CookService;
import com.khramovich.course.service.DishService;
import com.khramovich.course.service.Dish_setService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DishService dishService;

    @Autowired
    private Dish_setService dish_setService;

    @GetMapping("/main")
    public String main(Model model) {
        return "admin/main";
    }

    @GetMapping("/set")
    public String addMenu(Model model) {
        model.addAttribute("dishes", dishService.findAll());
        return "admin/add_set";
    }

    @PostMapping("/set")
    public String addMenu(@RequestParam(value = "check") Long[] ids, @ModelAttribute("name") String name,
                          @ModelAttribute("description") String description, @ModelAttribute("file") MultipartFile file,
                          HttpServletRequest request) {
        Dish_set dish_set = new Dish_set();
        dish_set.setName(name);
        dish_set.setDescription(description);
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                dish_set.setImage(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Set<Dish> dishes = new HashSet<Dish>();
        for (int id = 0; id < ids.length; id++) {
            dishes.add(dishService.findById(ids[id]));
            System.out.println(ids[id]);
        }
        dish_set.setDishes(dishes);
        dish_setService.save(dish_set);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
