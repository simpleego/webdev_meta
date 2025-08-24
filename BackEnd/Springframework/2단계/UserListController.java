package com.simple.spring01.controller;

import com.simple.spring01.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserListController {

    @GetMapping("/users")
    public String users(Model model) {
        List<User> userList = List.of(
                new User("홍길동", 25, "hong@example.com"),
                new User("김철수", 30, "kim@example.com"),
                new User("이영희", 22, "lee@example.com")
        );
        model.addAttribute("users", userList);
        return "userList";
    }
}