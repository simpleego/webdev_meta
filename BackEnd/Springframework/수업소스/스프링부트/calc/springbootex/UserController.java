package com.simple.springbootex;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
