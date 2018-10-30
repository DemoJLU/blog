package com.danxiaochong.blog.mapper;

import com.danxiaochong.blog.pojo.LoginLog;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginLogMapper {

    void insertLoginLog(LoginLog loginLog);

}
