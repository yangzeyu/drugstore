package com.jsmscp.dr.controller.api;


import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.MenuDto;
import com.jsmscp.dr.entity.SysRole;
import com.jsmscp.dr.service.SysMenuService;
import com.jsmscp.dr.service.SysRoleService;
import com.jsmscp.dr.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/common")
public class ApiCommonController {

    private SysRoleService sysRoleService;

    private SysMenuService sysMenuService;

    @GetMapping("findAllRole")
    public Response allRoles() {
        List<SysRole> roles = sysRoleService.findAllRoles(Constant.IS_DELETED_NO);
        List<Map<String, Object>> infos = roles.stream().map(r -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", r.getId());
            m.put("name", r.getName());
            return m;
        }).collect(Collectors.toList());
        return Response.buildSuccess(infos);
    }

    @GetMapping("findAllMenu")
    public Response allMenus() {
        List<MenuDto> menus = sysMenuService.findAllMenuRoles();
        return Response.buildSuccess(menus);
    }

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }
}
