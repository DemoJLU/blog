package com.danxiaochong.blog.controlller;

import com.danxiaochong.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @RequestMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "loginCheck", method = {RequestMethod.POST})
    public String loginCheck(@RequestBody Map<String, String> params, HttpServletRequest request) {
        String username = params.get("username");
        String password = params.get("password");
        String remember = params.get("remember");
        boolean flag = userService.hasMatchUser(username,password);
        String code = "1";
        if (!flag){
            code = "0";
        }
        return  code;
    }


}
