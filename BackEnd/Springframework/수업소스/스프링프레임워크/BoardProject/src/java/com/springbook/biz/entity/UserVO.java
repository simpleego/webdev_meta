package com.springbook.biz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               // getter/setter 모두 생성
@AllArgsConstructor // 모든 매개변수를 설정하는 생성자
@NoArgsConstructor  // 기본 생성자 추가
public class UserVO {
    private String id;
    private String name;
    private String password;
    private String role;
}
