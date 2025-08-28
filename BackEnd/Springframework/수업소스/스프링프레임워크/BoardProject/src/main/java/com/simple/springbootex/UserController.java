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
//        List<User> users = new ArrayList<User>();
//        User  user1 = new User(1, "홍길동", "1234", "simple@gmail.com");
//        User  user2 = new User(2, "김길동", "1234", "simple@gmail.com");
//        User  user3 = new User(3, "최길동", "1234", "simple@gmail.com");
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);

//        List<User> users = List.of(
//             new User(1, "홍길동", "1234", "simple@gmail.com"),
//             new User(2, "김길동", "1234", "simple@gmail.com"),
//             new User(3, "최길동", "1234", "simple@gmail.com")
//        );

        //model.addAttribute("users", users);
        return "userList";
    }

}
