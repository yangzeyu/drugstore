package com.jsmscp.dr.controller;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.MenuDto;
import com.jsmscp.dr.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice(basePackages = "com.jsmscp.dr.controller")
public class MenuAdvice {

    SysMenuService sysMenuService;

    @ModelAttribute("menus")
    public List<MenuDto> menus() {
        return sysMenuService.findAllMenu(Constant.IS_DELETED_NO);
    }

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }
}
