package com.jsmscp.dr.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/user")
public class WebSysUserController {

    @RequestMapping("index")
    public String userList() {
        return "sys/user/index";
    }
}
