package com.simple.springbootex;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/")
    public String sayHello(){
        return "redirect:/index.html";
    }

    @GetMapping("/hi")
    @ResponseBody
    public String hello(){
        System.out.println("get mapping /");
        return "<h1>Hello hi!</h1>";
    }
    @GetMapping("/about")
    @ResponseBody
    public String about(){
        System.out.println("get mapping /");
        return "<h1>About springBoot</h1>";
    }
}
