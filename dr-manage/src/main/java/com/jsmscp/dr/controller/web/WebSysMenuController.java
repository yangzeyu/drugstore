package com.jsmscp.dr.controller.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/menu")
public class WebSysMenuController {

    @RequestMapping("index")
    @PreAuthorize("hasRole('CATALOG_LIST')")
    public String menuList() {
        return "sys/menu/index";
    }

    @RequestMapping("{menuId}/operation/index")
    public String operation(@PathVariable(name = "menuId") String menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return "sys/menu/operation/index";
    }

}


