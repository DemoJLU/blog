package com.danxiaochong.blog.controlller;

import com.danxiaochong.blog.pojo.LoginCommand;
import com.danxiaochong.blog.pojo.User;
import com.danxiaochong.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

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

    @ResponseBody
    @RequestMapping(value = "loginCheck", method = {RequestMethod.POST})
    public String loginCheck(@RequestBody Map<String, String> params, HttpServletRequest request) {
        String userId = params.get("userid");
        String password = params.get("password");
        String remember = params.get("remember");
        return  null;
    }


}
