package com.danxiaochong.blog.controlller;

import com.danxiaochong.blog.pojo.LoginCommand;
import com.danxiaochong.blog.pojo.User;
import com.danxiaochong.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @RequestMapping(value = "/loginPage")
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/loginCheck")
    public ModelAndView loginCheck(HttpServletRequest request, String userName,String passWord ) {
        boolean isValidUser = userService.hasMatchUser(userName, passWord);
        if (!isValidUser) {
            return new ModelAndView("login", "error", "用户名或密码错误。");
        } else {
            User user = userService.findUserByUserName(userName);
            user.setLastIp(request.getLocalAddr());
            user.setLastVisit(new Date());
            userService.loginSuccess(user);
            request.getSession().setAttribute("user", user);
            return new ModelAndView("main");
        }
    }


}
