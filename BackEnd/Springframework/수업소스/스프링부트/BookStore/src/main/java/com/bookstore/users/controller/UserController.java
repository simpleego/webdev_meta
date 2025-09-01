package com.bookstore.users.controller;

import com.bookstore.users.entity.User;
import com.bookstore.users.repository.UserRepository;
import com.bookstore.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        return "users/addUser";
    }

    // User 등록
    @PostMapping("/user/add")
    public String addUser(@ModelAttribute User user) {

        userService.register(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String userList(Model model){
        System.out.println("UserController userList");
        model.addAttribute("users",userService.findAll());
        return "users/userList";
    }

    @GetMapping("/user/edit/{id}")
    public String userEditForm(@PathVariable String id, Model model){
           User user = (User) userService.findById(id).orElseThrow(
                   ()->new IllegalArgumentException("Invaild user id "));
           model.addAttribute("user",user);
        System.out.println("user : "+user);
        return "users/editUser";
    }

    @PostMapping("/user/edit")
    public String userEdit(@ModelAttribute User user){
        userService.update(user);
        return "redirect:/user/list";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam String id) {
        userService.deleteById(id);
        return "redirect:/user/list";
    }

}
