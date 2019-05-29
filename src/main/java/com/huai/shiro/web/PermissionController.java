package com.huai.shiro.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
    @RequestMapping("reload")
    public String reload() throws Exception{
        return "list";
    }
}
