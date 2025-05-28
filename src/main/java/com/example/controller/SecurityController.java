package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class SecurityController {

    @GetMapping("/") //HTTPのGETリクエストを受け付ける
    public String index() {
        return "index"; //index.htmlをレスポンスする
    }

    @GetMapping("/login")
    public String getlogin() {
        return "login";
    }
    
    @PostMapping("/login")
    public String postlogin() {
        return "home";
    }
//    @GetMapping("/home")
//    public String home() {
//        return "home";
//    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
    
    @GetMapping("/logout")
    public String getlogout() {
        return "index";
    }
    
//    @GetMapping("/delete/{id}")
//	public String deleteTeacher(@PathVariable Long id) {
//		service.deleteByPrimaryKey(id);
//		return "redirect:/home";
//	}
}