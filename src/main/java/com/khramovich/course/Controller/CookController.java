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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CookController {

    @Autowired
    private CookService cookService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CookValidator cookValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model){
        System.out.println("fewf");
        model.addAttribute("userForm", new Cook());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") Cook userForm, BindingResult bindingResult, Model model){
        cookValidator.validate(userForm, bindingResult);
        System.out.println("fewf");
        if(bindingResult.hasErrors()){
            return "registration";
        }

        cookService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout){
        if (error != null){
            model.addAttribute("error", "Username or password is incorrect");
        }

        if(logout!= null){
            model.addAttribute("message", "Logged out successfully");
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(){
        return "redirect:/welcome";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName);
        String name = null;
        if (!currentPrincipalName.equals("anonymousUser")){
            name = currentPrincipalName;
        }
        model.addAttribute("name", currentPrincipalName);
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model){
        return "admin";
    }
}