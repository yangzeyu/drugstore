package com.jsmscp.dr.controller.api;

import com.jsmscp.dr.entity.SysMenuOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.entity.SysMenu;
import com.jsmscp.dr.entity.SysRole;
import com.jsmscp.dr.service.SysRoleService;
import com.jsmscp.dr.util.Response;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/sysRole")
public class ApiSysRoleController {

    private static final Logger LOG = LoggerFactory.getLogger(ApiSysRoleController.class);
    private SysRoleService sysRoleService;


    @Autowired
    public ApiSysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Response delete(@RequestParam(value = "roleId") Integer roleId) {
        boolean back = sysRoleService.delete(roleId);
        Response response;
        if (back) {
            response = Response.buildSuccess("");
        } else {
            response = Response.fail("角色下存在用户,不允删除!");
        }
        return response;
    }

    /**
     * 修改角色菜单
     *
     * @param menuIds
     * @param roleId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "editRoleMenu", method = RequestMethod.POST)
    public Response editRoleMenu(@RequestParam(value = "menuIds") String menuIds,
                                 @RequestParam(value = "roleId") Integer roleId,
                                 String operationIds) {
        sysRoleService.editRoleMenu(menuIds, operationIds, roleId);
        return Response.success();
    }

    /**
     * 新增角色
     * @param roleName
     * @param code
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Response add(@RequestParam(value = "roleName") String roleName,
                        @RequestParam(value = "code", required = false) String code) {
        Integer id = sysRoleService.addRole(roleName, code);
        Response response = Response.buildSuccess(id);
        return response;
    }

    /**
     * 修改角色
     * @param roleId
     * @param roleName
     * @param roleCode
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Response edit(@RequestParam(value = "roleId") Integer roleId,
                         @RequestParam(value = "roleName", required = false) String roleName,
                         @RequestParam(value = "roleCode", required = false) String roleCode) {
        sysRoleService.edit(roleId, roleName, roleCode);
        return Response.buildSuccess("success");
    }

    /**
     * 查询所有角色
     *
     * @param key
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list(@RequestParam(value = "key", required = false) String key,
                         @RequestParam(value = "pageNo") Integer pageNo) {
        Response response = Response.buildSuccess("");
        List<SysRole> list = sysRoleService.list(key, pageNo);
        HashMap<String, Object> map = sysRoleService.findAllCount(key);
        map.put("list", list);
        map.put("pageSize", Constant.DEFAULT_PAGE_SIZE);
        map.put("pageNo", pageNo);
        response.setData(map);
        return response;
    }

    /**
     * 查询单个角色
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "findOne", method = RequestMethod.GET)
    public Response findOne(@RequestParam(value = "roleId", required = false) Integer roleId) {
        SysRole role = sysRoleService.findOne(roleId);
        return Response.buildSuccess(role);
    }

    /**
     * 根据角色查询菜单
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "findMenuByRole", method = RequestMethod.GET)
    public Response findMenuByRole(@RequestParam(value = "roleId") Integer roleId) {
        List<SysMenu> menu = sysRoleService.findMenuByRole(roleId);
        return Response.buildSuccess(menu);
    }

    @RequestMapping(value = "findOperationByRole", method = RequestMethod.GET)
    public Response findOperationByRole(@RequestParam(value = "roleId") Integer roleId) {
        List<SysMenuOperation> menu = sysRoleService.findOperationByRole(roleId);
        return Response.buildSuccess(menu);
    }

}
