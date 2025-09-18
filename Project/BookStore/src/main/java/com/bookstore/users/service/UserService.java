package com.bookstore.users.service;

import com.bookstore.users.entity.User;
import com.bookstore.users.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public int join(User user) {
        System.out.println("==> user joined");
        validateDuplicateUser(user);
        userMapper.save(user);
        return user.getCustId();
    }

    // Service 메서드는 그대로 사용
    private void validateDuplicateUser(User user) {
        userMapper.findByUsername(user.getUserName())  // getUserName() → getUsername()
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });
    }

//    private void validateDuplicateUser(User user) {
//        userMapper.findByUsername(user.getUserName())
//                .ifPresent(m ->{
//                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
//                });
//
//    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    // 로그인
    public Optional<User> login(String username, String password) {
        System.out.println("Service --> login username: " + username);
        return userMapper.findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }

    public Optional<User> findById(int id) {
        return userMapper.findById(id);
    }

    public void update(User user) {
        userMapper.updateUser(user);
    }

    public void deleteById(int id) {
        userMapper.deleteById(id);
    }

    public boolean isUsernameDuplicate(String username) {
        System.out.println("isUsernameDuplicate username: " + username);
        return userMapper.existsByUsername(username) > 0;
    }
}
