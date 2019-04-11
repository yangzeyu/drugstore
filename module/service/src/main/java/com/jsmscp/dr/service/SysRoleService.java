package com.jsmscp.dr.service;


import com.jsmscp.dr.entity.SysMenu;
import com.jsmscp.dr.entity.SysMenuOperation;
import com.jsmscp.dr.entity.SysRole;

import java.util.HashMap;
import java.util.List;

public interface SysRoleService {

    /**
     * 添加角色
     * @param name
     * @param code
     * @return
     */
    Integer addRole(String name, String code);

    /**
     * 修改角色菜单
     * @param menuIds
     * @param roleId
     */
    void editRoleMenu(String menuIds, String operationIds, Integer roleId);

    /**
     * 查询所有角色
     * @return
     */
    List<SysRole> findAllRoles(Byte isDeleted);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    boolean delete(Integer roleId);

    /**
     * 查询角色列表
     * @param key
     * @param pageNo
     * @return
     */
    List<SysRole> list(String key, Integer pageNo);

    /**
     * 查询角色列表总页数
     * @param key
     * @return
     */
    HashMap<String, Object> findAllCount(String key);

    /**
     * 查询单个角色
     * @param roleId
     * @return
     */
    SysRole findOne(Integer roleId);

    /**
     * 根据菜单查询角色
     * @param roleId
     * @return
     */
    List<SysMenu> findMenuByRole(Integer roleId);

    List<SysMenuOperation> findOperationByRole(Integer roleId);

    List<SysRole> findAllRole();

    void edit(Integer roleId, String roleName, String roleCode);
}
