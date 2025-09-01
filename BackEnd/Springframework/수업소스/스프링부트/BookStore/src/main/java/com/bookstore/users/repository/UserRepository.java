package com.bookstore.users.repository;

import com.bookstore.users.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Optional<Object> findById(String id) {
        System.out.println("==> user findById"+id);
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = jdbc.queryForObject(sql, userRowMapper(), id);
        return Optional.ofNullable(user);
    }

    public List<User> findAll(){
        String sql = "SELECT * FROM users";
        return jdbc.query(sql,userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            Date birthDate = rs.getDate("birthday");
            LocalDate birthDay = birthDate != null ? ((java.sql.Date) birthDate).toLocalDate() : null;
            return new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    birthDay,
                    rs.getString("email")
            );
        };
    }

    public void update(User user) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        jdbc.update(sql,
                user.getName(),
                user.getEmail(),
                user.getId()
        );

    }

    public void deleteById(String id) {
        String sql = "DELETE FROM users WHERE id = ?";
        int rowAffected = jdbc.update(sql, id);
        if (rowAffected > 0) {
            System.out.println(rowAffected);
        }else {
            System.out.println("삭제할 사용자가 없습니다."+id);
        }
    }
}
