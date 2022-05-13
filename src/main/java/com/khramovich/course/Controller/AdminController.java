package com.khramovich.course.Controller;

import com.khramovich.course.models.Cook;
import com.khramovich.course.models.Dish;
import com.khramovich.course.models.Dish_set;
import com.khramovich.course.repository.RoleDao;
import com.khramovich.course.service.CookService;
import com.khramovich.course.service.DishService;
import com.khramovich.course.service.Dish_setService;
import com.khramovich.course.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DishService dishService;

    @Autowired
    private Dish_setService dish_setService;

    @Autowired
    private CookService cookService;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private OrderService orderService;

    @GetMapping("/main")
    public String main(Model model) {
        return "admin/main";
    }

    @GetMapping("/set")
    public String addMenu(Model model) {
        model.addAttribute("dishes", dishService.findAll());
        return "set/add_set";
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

    @GetMapping("/edit")
    public String editSet(Model model) {
        model.addAttribute("sets", dish_setService.findAll());
        return "set/edit_set";
    }

    @PostMapping("/set/delete/{id}")
    public String deleteSet(@PathVariable("id") Long setId, HttpServletRequest request) {
        dish_setService.deleteById(setId);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/set/update/{id}")
    public String updateSet(@PathVariable("id") Long setId, Model model) {
        model.addAttribute("set", dish_setService.getById(setId));
        model.addAttribute("dishes", dishService.findAll());
        return "set/update_one";
    }

    @PostMapping("/set/update/{id}")
    public String updateSet(@PathVariable("id") Long setId, @RequestParam(value = "check") Long[] ids,
                            @ModelAttribute("name") String name, @ModelAttribute("description") String description,
                            @ModelAttribute("file") MultipartFile file, HttpServletRequest request){
        Dish_set dish_set = dish_setService.getById(setId);
        dish_set.setName(name);
        dish_set.setDescription(description);
        if (!file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                dish_set.setImage(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boolean matchCounter;
        for (Dish dish : dish_set.getDishes()){
            matchCounter = false;
            for (int i = 0; i < ids.length; i++){
                if (Objects.equals(ids[i], dish.getDish_id())){
                    matchCounter = true;
                }
            }
            if (!matchCounter){
                dish_set.getDishes().remove(dish);
            }
        }
        dish_setService.save(dish_set);
        String referer = request.getHeader("Referer");
        return "redirect:/admin/edit";
    }

    @GetMapping("/users")
    public String showUsers(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("cooks", cookService.findAll());
        model.addAttribute("admin_role", roleDao.getById(2L));
        model.addAttribute("username", username);
        return "admin/show_users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        cookService.deleteById(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/user/{id}")
    public String makeUser(@PathVariable("id") Long id){
        Cook cook = cookService.getById(id);
        cook.getRoles().remove(roleDao.getById(2L));
        cookService.update(cook);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/admin/{id}")
    public String makeAdmin(@PathVariable("id") Long id){
        Cook cook = cookService.getById(id);
        cook.getRoles().add(roleDao.getById(2L));
        cookService.update(cook);
        return "redirect:/admin/users";
    }

    @GetMapping("/order")
    public String acceptOrder(Model model){
        model.addAttribute("orders", orderService.findAll());
        return "admin/order";
    }

    @GetMapping("/order/show/{id}")
    public String showOrder(@ModelAttribute("id") Long id, Model model){
        model.addAttribute("order", orderService.getById(id));
        return "admin/show_order";
    }

    @PostMapping("/order/complete/{id}")
    public String showOrder(@ModelAttribute("id") Long id, HttpServletRequest request){
        orderService.deleteById(id);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}