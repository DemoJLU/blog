package com.danxiaochong.blog.mapper;

import com.danxiaochong.blog.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    int getMatchCount(String userName, String password);

    User findUserByUserName(String userName);

//    void updateLoginInfo(User user);
}
