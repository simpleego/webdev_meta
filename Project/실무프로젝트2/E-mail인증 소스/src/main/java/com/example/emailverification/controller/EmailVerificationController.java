package com.example.emailverification.controller;


import com.example.emailverification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmailVerificationController {

    private final EmailService emailService;

    public EmailVerificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/send-code")
    @ResponseBody
    public String sendVerificationCode(@RequestParam String email) {
        try {
            emailService.sendVerificationCode(email);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    @PostMapping("/verify-code")
    @ResponseBody
    public String verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = emailService.verifyCode(email, code);
        return isValid ? "success" : "fail";
    }

    @PostMapping("/signup")
    @ResponseBody
    public String signup(@RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String name) {
        // TODO: 실제 회원가입 로직 구현 (DB 저장 등)
        return "회원가입 완료: " + name + " (" + email + ")";
    }
}