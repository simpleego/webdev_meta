package com.simple.spring01.model;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Date;

public class User {

    @NotBlank(message = "ID는 필수입니다.")
    private String id;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "생년월일은 필수입니다.")
    private LocalDate birthDay;

    @Email(message = "유효한 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    // 기본 생성자
    public User() {}

    // 생성자
    public User(String id, String name, LocalDate birthDay, String email) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.email = email;
    }

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getBirthDay() { return birthDay; }
    public void setBirthDay(LocalDate  birthDay) { this.birthDay = birthDay; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}