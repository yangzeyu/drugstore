package com.jsmscp.dr.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.jsmscp.dr.util.Response;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("echo")
    @ResponseBody
    public Response echo() {
        return Response.buildSuccess("hello world");
    }

    /**
     * 登录
     * @return 登录页面
     */
    @GetMapping("/home")
    public String home(@RequestParam Optional<String> error) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "index1";

    }


    /**
     * 登录
     * @return 登录页面
     */
    @GetMapping("login")
    public String login(@RequestParam Optional<String> error) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return "login";
    }

    /**
     * 主页
     * @return 主页面
     */
    @GetMapping("/")
    public String welcome(@RequestParam Optional<String> error) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "index1";

    }
}
