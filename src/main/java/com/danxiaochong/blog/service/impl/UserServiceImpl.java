package com.danxiaochong.blog.service.impl;

import com.danxiaochong.blog.mapper.LoginLogMapper;
import com.danxiaochong.blog.mapper.UserMapper;
import com.danxiaochong.blog.pojo.LoginLog;
import com.danxiaochong.blog.pojo.User;
import com.danxiaochong.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginLogMapper loginLogMapper;

    public boolean hasMatchUser(String userName, String password) {
        int matchCount = userMapper.getMatchCount(userName, password);
        return matchCount > 0;
    }

    public User findUserByUserName(String userName) {
        return userMapper.findUserByUserName(userName);
    }

    @Transactional
    public void loginSuccess(User user) {
        user.setCredits(5 + user.getCredits());
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getUserId());
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(user.getLastVisit());
        //    userMapper.updateLoginInfo(user);
        //    loginLogMapper.insertLoginLog(loginLog);
    }


}
