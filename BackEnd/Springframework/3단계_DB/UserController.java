package com.simple.spring01.controller;

import com.simple.spring01.model.User;
import com.simple.spring01.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/users/{id}")
    public String userDetail(@PathVariable String id, Model model) {
        System.out.println("-->"+id);
        User user = (User) repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        model.addAttribute("user", user);
        return "userDetail";
    }

    @GetMapping("/users/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        User user = (User) repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        model.addAttribute("user", user);
        System.out.println("회원:"+user);
        return "editUser";
    }

    @PostMapping("/users/edit")
    public String editUser(@ModelAttribute User user) {
        repo.update(user); // UserRepository에 update 메서드가 있어야 함
        return "redirect:/users";
    }

}