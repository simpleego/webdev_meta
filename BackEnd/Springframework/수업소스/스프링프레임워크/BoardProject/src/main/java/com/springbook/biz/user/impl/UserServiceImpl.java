package com.springbook.biz.user.impl;

import com.springbook.biz.entity.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDao) {
        this.userDAO = userDao;
    }

    @Override
    public UserVO getUser(UserVO vo) {
        return userDAO.getUser(vo);
    }

    @Override
    public UserVO getUserList(UserVO vo) {
        return null;
    }

    @Override
    public void addUser(UserVO vo) {

    }

    @Override
    public void updateUser(UserVO vo) {

    }

    @Override
    public void deleteUser(UserVO vo) {

    }

}
