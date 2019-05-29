package com.huai.shiro.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(Model model, HttpServletRequest request)throws Exception{
        return "forward:login.jsp";
    }
}
