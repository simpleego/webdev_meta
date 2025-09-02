package com.bookstore.users.service;

import com.bookstore.users.entity.User;
import com.bookstore.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void register(User user) {
        userRepo.add(user);
    }

    /**
     * 회원 가입
     */
    public String join(User customer) {
        // 아이디 중복 확인
        validateDuplicateMember(customer);
        userRepo.save(customer);
        return customer.getCustId();
    }

    private void validateDuplicateMember(User customer) {
        userRepo.findByUsername(customer.getUserName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    // 로그인
    public Optional<User> login(String username, String password) {
        return userRepo.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }


    public Optional<User> findById(String id) {
        return userRepo.findById(id);
    }

    public void update(User user) {
        userRepo.update(user);
    }

    public void deleteById(String id) {
        userRepo.deleteById(id);
    }
}
