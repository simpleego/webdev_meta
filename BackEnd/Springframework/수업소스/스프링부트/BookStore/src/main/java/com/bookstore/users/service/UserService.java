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

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public Optional<Object> findById(String id) {
        return userRepo.findById(id);
    }

    public void update(User user) {
        userRepo.update(user);
    }

    public void deleteById(String id) {
        userRepo.deleteById(id);
    }
}
