package com.simple.springbootex;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddController {

    @GetMapping("/calc")
    public String showCalc(){
        return "add";
    }

    @PostMapping("/calc")
    public String add(@RequestParam(name = "num1", defaultValue = "0") String num1,
                      @RequestParam(name = "num2", defaultValue = "0") String num2,
                      @RequestParam(name = "op") String op,
                      Model model)  {

        System.out.println("==>"+num1);
        System.out.println("==>"+num2);
        int result = 0;
        if (op.equals("+")) {
            result = Integer.parseInt(num1)+Integer.parseInt(num2);
        }else if (op.equals("-")) {
            result = Integer.parseInt(num1)-Integer.parseInt(num2);
        }
        System.out.println("==>"+result);
        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("result", String.valueOf(result));
        return "add";
    }


}
