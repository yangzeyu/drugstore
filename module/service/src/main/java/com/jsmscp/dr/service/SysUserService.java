package com.jsmscp.dr.service;


import org.apache.ibatis.annotations.Param;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.entity.SysMenu;
import com.jsmscp.dr.entity.SysUser;


import java.util.HashMap;
import java.util.List;


public interface SysUserService {


    /**
     * 根据用户id查找用户.
     *
     * @param userId 用户id
     * @return 用户对象
     */
    SysUser findOne(Integer userId);


    /**
     * @return
     */
    Integer addUser(String nickName, String userName, String password, Integer roleId) throws BusinessException;


    /**
     * 查询所有的用户
     *
     * @param pageNo
     * @return
     */
    List<SysUser> list(Integer pageNo, String key, Integer roleId, Integer status);

    /**
     * 删除用户
     *
     * @param id
     */
    void updateIsDeleted(Integer id);

    /**
     * 查询用户列表总页数
     *
     * @param key
     * @param roleId
     * @param status
     * @return
     */
    HashMap<String, Object> findTotal(String key, Integer roleId, Integer status);

    /**
     * 修改用户密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     * @throws Exception
     */
    int updatePassword(Integer userId, String oldPassword, String newPassword);

    /**
     * 更新用户信息
     *
     * @param userId
     * @param nickName
     * @param userName
     * @param roleId
     */
    void update(Integer userId, String nickName, String userName, Integer roleId)
            throws BusinessException;

    SysUser getNormalUserByName(@Param("userName") String userName);

    List<SysMenu> findMenuByUserId(Integer id);

    List<String> findAllRoles(Integer userId);
}
