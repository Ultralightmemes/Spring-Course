package com.khramovich.course.Controller;

import com.khramovich.course.Models.Cook;
import com.khramovich.course.Service.CookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private CookService cookService;

    @GetMapping
    public String showMainPage(Model model){
        return "main/main";
    }

    @GetMapping("/account")
    public String manageAccount(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Cook cook = cookService.findByUsername(username);
        if (!username.equals("anonymousUser")) {
            System.out.println(cook.getName());
            model.addAttribute("name", cook.getName());
            model.addAttribute("surname", cook.getSurname());
            model.addAttribute("position", cook.getPosition());
            model.addAttribute("education", cook.getEducation());
            model.addAttribute("birthday", cook.getBirthday());
        }

        return "main/account";
    }

    @PostMapping("/account")
    public String manageAccount(@ModelAttribute("updateForm") Cook cook, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Cook userCook = cookService.findByUsername(username);
        userCook.setName(cook.getName());
        userCook.setSurname(cook.getSurname());
        userCook.setEducation(cook.getEducation());
//        userCook.setBirthday(cook.getBirthday());
        cookService.save(userCook);
        return "main/account";
    }
}
