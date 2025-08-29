package com.simple.springbootex;

import com.simple.springbootex.entity.User;
import com.simple.springbootex.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/user/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    // User 등록
    @PostMapping("/user/add")
    public String addUser(@ModelAttribute User user) {
        repo.add(user);
        return "redirect:/user/list";
    }


//    @GetMapping("/user")
//    public String user(Model model){
//        User  user = new User(1, "홍길동", "1234", "simple@gmail.com");
//        model.addAttribute("user", user);
//        return "user";
//    }

    @GetMapping("/user/list")
    public String userList(Model model){
        model.addAttribute("users",repo.findAll());
        return "userList";
    }

    @GetMapping("/user/edit/{id}")
    public String userEditForm(@PathVariable String id, Model model){
           User user = (User) repo.findById(id).orElseThrow(
                   ()->new IllegalArgumentException("Invaild user id "));
           model.addAttribute("user",user);
        System.out.println("user : "+user);
        return "editUser";
    }

    @PostMapping("/user/edit")
    public String userEdit(@ModelAttribute User user){
        repo.update(user);
        return "redirect:/user/list";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam String id) {
        repo.deleteById(id);
        return "redirect:/user/list";
    }

}
