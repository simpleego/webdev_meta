package com.simple.springbootex.repo;

import com.simple.springbootex.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void add(User user) {
        System.out.println("==> user added");
        String sql = "INSERT INTO users (id, name, password, birthday, email) VALUES (?, ?, ?, ?,?)";
        int result = jdbc.update(sql,
                user.getId(),
                user.getName(),
                user.getPassword(),
                java.sql.Date.valueOf(user.getBirthday()), // LocalDate → java.sql.Date
                user.getEmail()
        );
        if (result == 1) {
            System.out.println(result);
            System.out.println("회원 등록 성공");
        }else {
            System.out.println("회원등록 실패");
        }
    }
}
