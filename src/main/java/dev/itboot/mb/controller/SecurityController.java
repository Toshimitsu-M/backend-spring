package dev.itboot.mb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class SecurityController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

//    @GetMapping("/home")
//    public String home() {
//        return "home";
//    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}