package com.jsmscp.dr.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.entity.SysUser;
import com.jsmscp.dr.service.SysUserService;
import com.jsmscp.dr.util.Response;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/sysUser")
public class ApiSysUserController {

    private static final Logger LOG = LoggerFactory.getLogger(ApiSysUserController.class);

    private SysUserService sysUserService;

    @Autowired
    public ApiSysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }


    /**
     * 列表显示用户
     *
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                         @RequestParam(value = "key", required = false) String key,
                         @RequestParam(value = "roleId", required = false) Integer roleId,
                         @RequestParam(value = "status", required = false) Integer status) {
        Response response = Response.buildSuccess("");
        List<SysUser> list = sysUserService.list(pageNo, key, roleId, status);
        HashMap<String, Object> map = sysUserService.findTotal(key, roleId, status);
        map.put("list", list);
        map.put("pageSize", Constant.DEFAULT_PAGE_SIZE);
        map.put("pageNo", pageNo);
        response.setData(map);
        return response;
    }

    /**
     * 新增用户
     *
     * @param nickName
     * @param userName
     * @param password
     * @param roleId
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Response add(@RequestParam(value = "nickName", required = false) String nickName,
                        @RequestParam(value = "userName", required = false) String userName,
                        @RequestParam(value = "password", required = false) String password,
                        @RequestParam(value = "roleId", required = false) Integer roleId) {
        Response response;
        try {
            Integer id = sysUserService.addUser(nickName, userName, password, roleId);
            response = Response.buildSuccess(id);
        } catch (BusinessException e) {
            response = Response.fail(e.getMessage());
        }
        return response;
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Response delete(@RequestParam(value = "userId") Integer userId) {
        sysUserService.updateIsDeleted(userId);
        Response response = Response.buildSuccess("");
        return response;
    }

    /**
     * 更新用户(角色)
     *
     * @param nickName
     * @param userName
     * @param password
     * @param roleId
     * @param userId
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Response update(@RequestParam(value = "nickName", required = false) String nickName,
                           @RequestParam(value = "userName", required = false) String userName,
                           @RequestParam(value = "password", required = false) String password,
                           @RequestParam(value = "roleId", required = false) Integer roleId,
                           @RequestParam(value = "userId") Integer userId) {
        Response response;
        try {
            sysUserService.update(userId, nickName, userName, roleId);
            response = Response.buildSuccess("");
        } catch (BusinessException e) {
            response = Response.fail(e.getMessage());
        }
        return response;
    }

    /**
     * 更新密码
     *
     * @param oldPassword
     * @param newPassword
     * @param userId
     * @return
     */
    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public Response updatePassword(@RequestParam(value = "oldPassword", required = false) String oldPassword,
                                   @RequestParam(value = "newPassword", required = false) String newPassword,
                                   @RequestParam(value = "userId") Integer userId) {
        sysUserService.updatePassword(userId, oldPassword, newPassword);
        Response response = Response.buildSuccess("");
        return response;
    }

    /**
     * 查询单个用户
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "findOne", method = RequestMethod.GET)
    public Response updatePassword(@RequestParam(value = "userId") Integer userId) {
        SysUser user = sysUserService.findOne(userId);
        Response response = Response.buildSuccess(user);
        return response;
    }
}
