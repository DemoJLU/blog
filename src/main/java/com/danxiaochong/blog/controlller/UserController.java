package com.danxiaochong.blog.controlller;

import com.danxiaochong.blog.common.base.ErrCode;
import com.danxiaochong.blog.pojo.system.MenuNode;
import com.danxiaochong.blog.pojo.system.User;
import com.danxiaochong.blog.pojo.system.UserRole;
import com.danxiaochong.blog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/modifyPassword", method = { RequestMethod.POST })
    public Map<String, Object> ModifyPassword(@RequestBody Map<String, String> params, HttpSession httpSession) {
        int code;
        if (true) {
            User user = (User) httpSession.getAttribute("AUTH_USER");
            if (user != null) {
                if (params.get("password").equals(user.getPassWord())) { // 口令验证
                    user.setPassWord(params.get("newPas"));
                    code = userService.updateUser(user);
                    if (code == 0) {
                    }
                } else {
                    code = ErrCode.PASSWORD_ERROR;
                }
            } else {
                code = ErrCode.NOT_LOGIN;
            }
        } else {
            code = ErrCode.PASSWORD_ILLEGAL;
        }
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("code", code);
        ret.put("msg", ErrCode.getMessage(code));
        return ret;
    }

    @ResponseBody
    @RequestMapping(value = "/getRoleIDbyUser", method = { RequestMethod.GET })
    public Map<String, Object> getRoleIDbyUser(HttpSession httpSession) {
        logger.info("获取用户基本信息");
        User user = (User) httpSession.getAttribute("AUTH_USER");
        List<UserRole> roles = userService.getUserRole(user.getUserId());
        List<MenuNode> menus = userService.buildUserMenu(user.getUserId());
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("userId", user.getUserId());
        ret.put("userName", user.getUserName());
        ret.put("roles", roles);
        ret.put("menus", menus);
        return ret;
    }

    @ResponseBody
    @RequestMapping(value = "/modifyIdxUrl", method = { RequestMethod.POST })
    public Map<String, Object> modifyIdxUrl(@RequestParam(value = "userId") String userId,
                                            @RequestParam(value = "idxUrl") String userUrl, HttpSession session) {
        logger.info("提交首页显示");
        int code = 0;
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userUrl)) {
            code = userService.updateUserIdxUrl(userId, userUrl);
            if (code == 0) {
                User user = (User) session.getAttribute("AUTH_USER");
                user.setIdx_url(userUrl);
            }
        } else {
            code = ErrCode.INCOMPLETE_INFO;
        }
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("code", code);
        ret.put("msg", ErrCode.getMessage(code));
        return ret;
    }
}
