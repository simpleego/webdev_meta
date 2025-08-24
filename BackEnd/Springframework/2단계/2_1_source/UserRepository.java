package com.simple.spring01.repository;

import com.simple.spring01.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public List<User> findAll() {
        return users;
    }

    public void add(User user) {
        users.add(user);
    }

    public void deleteByEmail(String email) {
        users.removeIf(u -> u.getEmail().equals(email));
    }
}
