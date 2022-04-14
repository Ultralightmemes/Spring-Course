package com.khramovich.course.Controller;

import com.khramovich.course.Models.Cook;
import com.khramovich.course.Service.CookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private CookService cookService;

    @GetMapping
    public String showMainPage(Model model) {
        return "main/main";
    }

    @GetMapping("/account")
    public String manageAccount(Model model) throws UnsupportedEncodingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Cook cook = cookService.findByUsername(username);
        if (!username.equals("anonymousUser")) {
            model.addAttribute("name", cook.getName());
            model.addAttribute("surname", cook.getSurname());
            model.addAttribute("position", cook.getPosition());
            model.addAttribute("education", cook.getEducation());
            model.addAttribute("birthday", cook.getBirthday());
            if (cook.getImage() != null){
                model.addAttribute("avatar", addImage(cook.getImage()));
            }
            else {
                model.addAttribute("avatar", null);
            }
        }

        return "main/account";
    }

    @PostMapping("/account")
    public String manageAccount(@ModelAttribute("updateForm") Cook cook, @ModelAttribute("date") String date,
                                @ModelAttribute("file") MultipartFile file, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Cook userCook = cookService.findByUsername(username);
        userCook.setName(cook.getName());
        userCook.setSurname(cook.getSurname());
        userCook.setEducation(cook.getEducation());
        if (!date.isEmpty()) {
            userCook.setBirthday(parseDate(date));
        }
        userCook.setPassword(userCook.getPassword());
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                userCook.setImage(bytes);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        cookService.update(userCook);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    private java.sql.Date parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = null;
        try {
            parsed = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(parsed.getTime());
    }

    private String addImage(byte[] bytes) throws UnsupportedEncodingException {
        byte[] encodeBase64 = Base64.getEncoder().encode(bytes);
        return new String(encodeBase64, "UTF-8");
    }
}
