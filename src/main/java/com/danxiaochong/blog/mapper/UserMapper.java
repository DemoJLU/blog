package com.danxiaochong.blog.mapper;

import com.danxiaochong.blog.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    int getMatchCount(@Param(value = "userName")String userName, @Param(value = "passWord")String passWord);

    User findUserByUserName(String userName);

//    void updateLoginInfo(User user);
}
