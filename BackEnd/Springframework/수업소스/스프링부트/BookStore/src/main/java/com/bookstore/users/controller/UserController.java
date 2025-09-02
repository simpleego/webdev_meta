package com.bookstore.users.controller;

import com.bookstore.users.entity.User;
import com.bookstore.users.repository.UserRepository;
import com.bookstore.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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


    // 회원 가입 처리
    @PostMapping("/user/add")
    public String create(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.register(user);
        } catch (IllegalStateException e) {
            // 중복 아이디 예외 발생 시, 메시지를 담아 리다이렉트
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/add";
        }

        return "redirect:/"; // 가입 성공 시 홈으로 리다이렉트
    }

    // User 등록
//    @PostMapping("/user/add")
//    public String addUser(@ModelAttribute User user) {
//        userService.register(user);
//        return "redirect:/user/list";
//    }

    // login 폼으로 이동
    @GetMapping("/user/login")
    public String loginForm(){
        return "users/loginForm";
    }

    // 로그인 처리
    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {

        Optional<User> loginResult = userService.login(username, password);

        if (loginResult.isPresent()) {
            // 로그인 성공 시 세션 생성
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginResult.get());
            System.out.println("로그인 성공");
            return "redirect:/";
        } else {
            // 로그인 실패 시 메시지와 함께 리다이렉트
            System.out.println("로그인 실패");
            redirectAttributes.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "redirect:/user/login";
        }
    }

    // 로그아웃 처리
    @PostMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        System.out.println("로그아웃");
        HttpSession session = request.getSession(false); // 세션이 없으면 새로 생성하지 않음
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/";
    }

    // 회원목록
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
