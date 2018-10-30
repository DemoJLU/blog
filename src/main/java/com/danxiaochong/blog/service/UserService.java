package com.danxiaochong.blog.service;

import com.danxiaochong.blog.pojo.User;

public interface UserService {

    boolean hasMatchUser(String userName, String password);

    User findUserByUserName(String userName);

    void loginSuccess(User user);

}
