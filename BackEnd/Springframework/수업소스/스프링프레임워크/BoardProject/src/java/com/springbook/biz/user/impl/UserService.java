package com.springbook.biz.user.impl;

import com.springbook.biz.entity.UserVO;

public interface UserService {
    // CRUD 기능의 메소드 구현
    // 회원 등록
    UserVO getUser(UserVO vo);

    // 회원 목록
    UserVO getUserList(UserVO vo);

    // 회원 등록
    void addUser(UserVO vo);

    // 회원수정
    void updateUser(UserVO vo);

    // 회원삭제
    void deleteUser(UserVO vo);
}
