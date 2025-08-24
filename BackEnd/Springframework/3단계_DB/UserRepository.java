package com.simple.spring01.repository;

import com.simple.spring01.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.RowMapper; // ✅ Spring JDBC용

import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void save(User user) {
        String sql = "INSERT INTO users (id, name, birth_day, email) VALUES (?, ?, ?, ?)";
        jdbc.update(sql,
                user.getId(),
                user.getName(),
                java.sql.Date.valueOf(user.getBirthDay()), // java.util.Date → java.sql.Date
                user.getEmail()
        );
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbc.query(sql, userRowMapper());
    }

    public void add(User user) {
        String sql = "INSERT INTO users (id, name, birth_day, email) VALUES (?, ?, ?, ?)";
        jdbc.update(sql,
                user.getId(),
                user.getName(),
                java.sql.Date.valueOf(user.getBirthDay()), // LocalDate → java.sql.Date
                user.getEmail()
        );
    }

    // 사용자 수정
    public void update(User user) {
        String sql = "UPDATE users SET name = ?, birth_day = ?, email = ? WHERE id = ?";
        jdbc.update(sql,
                user.getName(),
                java.sql.Date.valueOf(user.getBirthDay()),
                user.getEmail(),
                user.getId());
    }

    public void deleteByEmail(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        int rowsAffected = jdbc.update(sql, email);
        if (rowsAffected == 0) {
            System.out.println("삭제할 사용자가 없습니다: " + email);
        }
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            Date birthDate = rs.getDate("birth_day");
            LocalDate birthDay = birthDate != null ? ((java.sql.Date) birthDate).toLocalDate() : null;
            return new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    birthDay,
                    rs.getString("email")
            );
        };
    }


    public Optional<Object> findById(String id) {
        System.out.println("==>" + id);
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = jdbc.queryForObject(sql, userRowMapper(), id);
        return Optional.of(user);

//        try {
//            User user = jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
//                User u = new User();
//                u.setId(rs.getString("id"));
//                System.out.println("id:" + u.getId());
//                u.setName(rs.getString("name"));
//                u.setBirthDay(rs.getDate("birth_day").toLocalDate());
//                u.setEmail(rs.getString("email"));
//                return u;
//            });
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
    }
}
