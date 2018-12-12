package com.danxiaochong.blog.mapper;

import com.danxiaochong.blog.pojo.system.Function;
import com.danxiaochong.blog.pojo.system.User;
import com.danxiaochong.blog.pojo.system.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    /**
     * 驗證登錄
     * */
    int getMatchCount(@Param(value = "userName")String userName, @Param(value = "passWord")String passWord);
    /**
     * 修改用戶信息
     * */
    void updateUser(User user);
    /**
     * 獲取用戶角色
     * */
    List<UserRole> getRoleByUser(String userId);
    /**
     * 加載菜單欄
     * */
    List<Function> queryFuncByUser(String userId);

    User findUserByUserName(String userName);

//    void updateLoginInfo(system user);
}
