package com.simple.springbootex;

import com.simple.springbootex.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @GetMapping("/greeting")
    public String greeting(Model model){
        model.addAttribute("name", "홍길동");
        model.addAttribute("age", "25");
        model.addAttribute("addr", "대전시 중구 선화동 123");
        model.addAttribute("tel", "010-1234-5678");
        return "user";
    }

    @GetMapping("/bye")
    public String bye(@RequestParam(name = "name", defaultValue = "손님")
                          String name, Model model){
        model.addAttribute("name", name);
        model.addAttribute("age", "25");
        model.addAttribute("addr", "대전시 중구 선화동 123");
        model.addAttribute("tel", "010-1234-5678");
        return "user";
    }

    @GetMapping("/user")
    public String user(Model model){
        User  user = new User(1, "홍길동", "1234", "simple@gmail.com");
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/user/list")
    public String userList(Model model){
//        List<User> users = new ArrayList<User>();
//        User  user1 = new User(1, "홍길동", "1234", "simple@gmail.com");
//        User  user2 = new User(2, "김길동", "1234", "simple@gmail.com");
//        User  user3 = new User(3, "최길동", "1234", "simple@gmail.com");
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);

        List<User> users = List.of(
             new User(1, "홍길동", "1234", "simple@gmail.com"),
             new User(2, "김길동", "1234", "simple@gmail.com"),
             new User(3, "최길동", "1234", "simple@gmail.com")
        );

        model.addAttribute("users", users);
        return "userList";
    }

}
