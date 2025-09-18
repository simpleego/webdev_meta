package com.bookstore.users.mapper;

import com.bookstore.users.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE custid > 4")
    List<User> findAll();

    // XML 정의 사용 - User 객체를 반환하고 Service에서 Optional로 래핑
    Optional<User> findById(int custId);

    void save(User user);

//    @Select("SELECT * FROM users WHERE username = #{username}")  // 파라미터명 수정
//    User findByUsername(String username);  // Optional 제거하고 Service에서 처리

    void updateUser(User user);  // User 객체로 변경

    @Delete("DELETE FROM users WHERE custid = #{id}")
    void deleteById(int id);

    // UserMapper.java
    @Select("SELECT * FROM users WHERE username = #{username}")
    Optional<User> findByUsername(String username);

    int existsByUsername(String username);


//    @Select("SELECT * FROM users  where custid > 4;")
//    List<User> findAll();
//
//    @Select("SELECT * FROM users WHERE custid = #{id}")
//    Optional<User> findById(int id);
//
//    void save(User user);
//
//    @Select("SELECT * FROM users WHERE username = #{userName}")
//    Optional<User> findByUsername(String userName);
//
//    void updateUser(int id);
//
//    @Delete("DELETE FROM users WHERE custid = #{id}")
//    void deleteById(int id);
//
//    int existsByUsername(String username); // 0이면 없음, 1 이상이면 중복
}
