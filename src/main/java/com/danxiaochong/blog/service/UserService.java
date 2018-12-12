package com.danxiaochong.blog.service;

import com.danxiaochong.blog.pojo.system.MenuNode;
import com.danxiaochong.blog.pojo.system.User;
import com.danxiaochong.blog.pojo.system.UserRole;

import java.util.List;

public interface UserService {
    /**
     * 登錄驗證
     * */
    boolean hasMatchUser(String userName, String password);

    User findUserByUserName(String userName);

    /**
     * 修改用戶信息
     * */
    int updateUser(User user);
    /**
     * 獲取用戶角色
     * */
    List<UserRole> getUserRole(String userId);
    /**
     * 加載菜單欄
     * */
    List<MenuNode> buildUserMenu(String userId);

}
