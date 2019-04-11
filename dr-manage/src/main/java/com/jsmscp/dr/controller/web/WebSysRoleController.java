package com.jsmscp.dr.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/role")
public class WebSysRoleController {

    @RequestMapping("index")
    public String roleList() {
        return "sys/role/index";
    }

}
