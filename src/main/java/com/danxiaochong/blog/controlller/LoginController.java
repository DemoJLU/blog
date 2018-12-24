package com.danxiaochong.blog.controlller;

import com.danxiaochong.blog.pojo.system.User;
import com.danxiaochong.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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


}
