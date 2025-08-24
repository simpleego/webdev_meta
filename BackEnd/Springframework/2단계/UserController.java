package com.simple.spring01.controller;

import com.simple.spring01.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user")
    public String user(Model model) {
        User user = new User("홍길동", 25, "hong@example.com");
        model.addAttribute("user", user);
        return "user";
    }
}