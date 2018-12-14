package com.danxiaochong.blog.service;

import com.danxiaochong.blog.pojo.system.MenuNode;
import com.danxiaochong.blog.pojo.system.User;
import com.danxiaochong.blog.pojo.system.UserRole;

import java.util.List;

public interface UserService {

    User getUserById(String userId);
    /**
     * 登錄驗證
     * */
    boolean hasMatchUser(String user_id, String password);
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
    /**
     * 更新用戶首頁菜單
     * */
    int updateUserIdxUrl(String userId,String idx_url);

}
