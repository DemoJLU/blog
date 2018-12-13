package com.danxiaochong.blog.service.impl;

import com.danxiaochong.blog.common.base.ErrCode;
import com.danxiaochong.blog.mapper.UserMapper;
import com.danxiaochong.blog.pojo.Function;
import com.danxiaochong.blog.pojo.MenuNode;
import com.danxiaochong.blog.pojo.User;
import com.danxiaochong.blog.pojo.UserRole;
import com.danxiaochong.blog.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    public User getUserById(String userId) {
        User user = null;
        try {
            user = userMapper.getUserById(userId);
        } catch (Exception e) {
            logger.error("getUserById error: ", e);
        }
        return user;
    }

    /**
     * 修改用戶信息
     * */
    @Override
    public int updateUser(User user){
        int errCode = 0;
        try {
            userMapper.updateUser(user);
        } catch (Exception e) {
            logger.error("updateUser error: ", e);
            errCode = ErrCode.DB_ERROR;
        }
        return errCode;
    }

    /**
     * 驗證登錄
     * */
    @Override
    public boolean hasMatchUser(String user_id, String password) {
        int matchCount = userMapper.getMatchCount(user_id, password);
        return matchCount > 0;
    }
    /**
     * 獲取用戶角色
     * */
    @Override
    public List<UserRole> getUserRole(String userId) {
        List<UserRole> roles = null;
        try {
            roles = userMapper.getRoleByUser(userId);
        } catch (Exception e) {
            logger.error("getUserRole error: ", e);
        }
        return roles;
    }
    /**
     * 更新用戶菜單
     * */
    @Override
    public int updateUserIdxUrl(String userId, String idx_url) {
        int errCode = 0;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("user_id", userId);
            map.put("idx_url", idx_url);
            userMapper.updateUserUrl(map);
        } catch (Exception e) {
            logger.error("updateUserIdxUrl error: ", e);
            errCode = ErrCode.DB_ERROR;
        }
        return errCode;
    }
    @Override
    public List<MenuNode> buildUserMenu(String userId) {
        List<MenuNode> menus = new ArrayList<MenuNode>();
        try {
            List<Function> funcList = userMapper.queryFuncByUser(userId);
            if (CollectionUtils.isNotEmpty(funcList)) {
                Map<Integer, List<MenuNode>> map = new HashMap<Integer, List<MenuNode>>();
                for (Function f : funcList) {
                    if (f.getFunc_level().compareTo("2") <= 0) {
                        MenuNode m = new MenuNode(f.getId(), f.getFunc_name(), f.getFunc_url(), f.getShow_order());
                        if (f.getParent_id() != null && f.getParent_id() > 0) {
                            if (map.get(f.getParent_id()) == null) {
                                map.put(f.getParent_id(), new ArrayList<MenuNode>());
                            }
                            map.get(f.getParent_id()).add(m);
                        } else {
                            menus.add(m);
                        }
                    }
                }
                for (MenuNode m : menus) {
                    m.setChildren(map.get(m.getId()));
                    if (m.getChildren() != null) {
                        Collections.sort(m.getChildren());
                    }
                }
                Collections.sort(menus);
            }
        } catch (Exception e) {
            logger.error("buildUserMenu error: ", e);
        }
        return menus;
    }
}
