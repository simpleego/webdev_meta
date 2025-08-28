package com.simple.springbootex.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class User {
    private String id;
    private String name;
    private String password;
    private LocalDate birthday;
    private String email;

    public User() {
    }

    public User(String id, String name, String password, LocalDate birthday, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.birthday = birthday;
        this.email = email;
    }
}
