package com.khramovich.course.Controller;

import com.khramovich.course.Models.Cook;
import com.khramovich.course.Service.CookService;
import com.khramovich.course.Service.SecurityService;
import com.khramovich.course.Validator.CookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class CookController {

    @Autowired
    private CookService cookService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CookValidator cookValidator;

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new Cook());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") Cook userForm, BindingResult bindingResult, Model model){
        cookValidator.validate(userForm, bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }

        cookService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout){
        if (error != null){
            model.addAttribute("error", "Username or password is incorrect");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(){
        return "redirect:/welcome";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        String name = "Таинственный гость";
        boolean is_authenticated = false;
        if (!currentPrincipalName.equals("anonymousUser")){
            name = currentPrincipalName;
            is_authenticated = true;
        }
        model.addAttribute("name", name);
        model.addAttribute("is_authenticated", is_authenticated);
        return "welcome";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        return "admin";
    }
}
