package com.bookstore.users.controller;

import com.bookstore.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserAPIController {
    private final UserService userService;

    public UserAPIController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/check-username")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam String username) {
        System.out.println("Checking username: " + username);

        boolean isDuplicate = userService.isUsernameDuplicate(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("duplicate", isDuplicate);
        return ResponseEntity.ok(response);
    }
}
