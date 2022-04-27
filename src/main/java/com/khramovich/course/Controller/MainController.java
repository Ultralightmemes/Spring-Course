package com.khramovich.course.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.khramovich.course.models.Cook;
import com.khramovich.course.repository.RoleDao;
import com.khramovich.course.service.CookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private CookService cookService;

    @GetMapping
    public String showMainPage(Model model, HttpServletRequest request) throws IOException {
        if (model.getAttribute("weather") == null) {
            model.addAttribute("city", "Minsk");
            Map<String, String> map = getWeather("Minsk");
            model.addAttribute("weather", map.get("weather"));
            model.addAttribute("icon", map.get("icon"));
            model.addAttribute("text", map.get("text"));
        }
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
            if (cook.getImage() != null) {
                model.addAttribute("avatar", addImage(cook.getImage()));
            } else {
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

    @PostMapping("/entry")
    public String adminEntry() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:../admin/main";
        } else {
            return "redirect:../main/account";
        }
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

    private Map getWeather(String city) throws IOException {
        URL url = new URL("http://api.weatherapi.com/v1/current.json?key=b7420ee867b648db80a150055221504&q="
                + city + "&aqi=no");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        Map<String, String> map = new HashMap<>();
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode != 200) {
            return map;
        }
        String json = "";
        String weather = null;
        if (responseCode != 200)
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        else {
            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                json += sc.nextLine();
            }
            sc.close();
            Gson gson = new Gson();
            System.out.println(json);
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

            map.put("weather", jsonObject
                    .get("current").getAsJsonObject()
                    .get("temp_c").getAsString());
            map.put("icon", jsonObject
                    .get("current").getAsJsonObject()
                    .get("condition").getAsJsonObject()
                    .get("icon")
                    .getAsString());
            map.put("text", jsonObject
                    .get("current").getAsJsonObject()
                    .get("condition").getAsJsonObject()
                    .get("text").getAsString());
        }
        return map;
    }
}
