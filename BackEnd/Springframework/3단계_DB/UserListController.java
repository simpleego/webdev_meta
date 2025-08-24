package com.simple.spring01.controller;

import com.simple.spring01.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class UserListController {

    @GetMapping("/userList")
    public String users(Model model) throws ParseException {
        String birthDateStr = "1990-05-15";
        String birthDateStr1 = "1990-05-15";
        String birthDateStr2 = "1990-05-15";
        DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate birthDay = LocalDate.parse(birthDateStr);
        LocalDate birthDay1 = LocalDate.parse(birthDateStr1);
        LocalDate birthDay2 = LocalDate.parse(birthDateStr2);

        List<User> userList = List.of(
                new User("user001","홍길동", birthDay, "hong@example.com"),
                new User("user002","김철수", birthDay1, "kim@example.com"),
                new User("user003","이영희",birthDay2,  "lee@example.com")
        );
        model.addAttribute("users", userList);
        return "userList";
    }
}