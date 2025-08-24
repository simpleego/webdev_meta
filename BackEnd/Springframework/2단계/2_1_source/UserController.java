package com.simple.spring01.controller;

import com.simple.spring01.model.User;
import com.simple.spring01.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", repo.findAll());
        return "userList";
    }

    @GetMapping("/users/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute User user) {
        repo.add(user);
        return "redirect:/users";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam String email) {
        repo.deleteByEmail(email);
        return "redirect:/users";
    }
}