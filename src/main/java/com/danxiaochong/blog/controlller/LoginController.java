package com.danxiaochong.blog.controlller;

import com.danxiaochong.blog.pojo.system.User;
import com.danxiaochong.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/")
@Api("登陆接口")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @ApiOperation("登陆跳转")
    @RequestMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @ApiOperation("用户名密码判断")
    @ResponseBody
    @RequestMapping(value = "loginCheck", method = {RequestMethod.POST})
    public String loginCheck(@RequestBody Map<String, String> params, HttpServletRequest request) {
        String user_id = params.get("username");
        String password = params.get("password");
        boolean flag = userService.hasMatchUser(user_id,password);
        String code = "1";
        if (!flag){
            code = "0";
        }else{
            User user = userService.getUserById(user_id);
            HttpSession session = request.getSession();
            session.setAttribute("AUTH_USER",user);
        }
        return  code;
    }

    @ApiOperation("注册")
    @ResponseBody
    @RequestMapping(value = "register", method = {RequestMethod.POST})
    public String register(@RequestParam Map<String, String> params) {
        String user_id = params.get("userNameR");
        String password = params.get("passwordR");
        String passwordRC = params.get("passwordRC");
        String code = "1";
        if (!password.equals(passwordRC)){
            code = "2";
        }
        boolean flag = userService.sameUserId(user_id);
        if (flag){
             code = "3";
        }else{
            int result = userService.addUser(params);
            if (result == 0){
                code = "4";
            }
        }
        return code;
    }


}
