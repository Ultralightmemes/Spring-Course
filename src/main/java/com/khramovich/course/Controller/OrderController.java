package com.khramovich.course.Controller;

import com.google.common.collect.ImmutableSet;
import com.khramovich.course.models.Cook;
import com.khramovich.course.models.Order;
import com.khramovich.course.service.CookService;
import com.khramovich.course.service.DishService;
import com.khramovich.course.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    DishService dishService;

    @Autowired
    OrderService orderService;

    @Autowired
    CookService cookService;

    @GetMapping("/basket")
    public String showBasket(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("dishes", cookService.findByUsername(username).getBasket());
        return "order/basket";
    }

    @PostMapping("/basket")
    public String saveBasket(@ModelAttribute("date") String date, @ModelAttribute("comment") String comment, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Order order = new Order();
        if (!date.isEmpty()) {
            order.setFinish_date(parseDate(date));
        }
        order.setComment(comment);
        order.setDishes(ImmutableSet.copyOf(cookService.findByUsername(username).getBasket()));
        order.setUser(cookService.findByUsername(username));
        orderService.save(order);
        Cook cook = cookService.findByUsername(username);
        cook.getBasket().clear();
        cookService.update(cook);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    private java.sql.Date parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = null;
        try {
            parsed = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(parsed.getTime());
    }
}
