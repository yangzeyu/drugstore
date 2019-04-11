package com.jsmscp.dr.service;


import com.jsmscp.dr.dto.MenuDto;
import com.jsmscp.dr.entity.SysMenu;
import com.jsmscp.dr.entity.SysMenuOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysMenuService {
    /**
     * 添加菜单
     *
     * @param name
     * @param icon
     * @param code
     * @param url
     * @param parentId
     */
    Integer addMenu(String name, String icon, String code, String url, Integer parentId, Integer num);

    /**
     * 更新菜单信息
     * @param id
     * @param name
     * @param code
     * @param icon
     * @param url
     * @param num
     * @param parentId
     */
    void updateMenu(Integer id, String name, String code, String icon, String url, Integer num, Integer parentId);

    /**
     * 查询单个菜单
     * @param menuId
     * @return
     */
    SysMenu findById(Integer menuId);

    /**
     * 列表查询菜单
     * @param key
     * @param parentId
     * @param pageNo
     * @return
     */
    List<SysMenu> list(String key, Integer pageNo, Integer parentId);

    /**
     * 删除菜单
     * @param id
     * @param isDeletedYes
     */
    void updateIsDeleted(Integer id, byte isDeletedYes);

    /**
     * 查询菜单列表总页数
     * @param key
     * @param parentId
     * @return
     */
    HashMap<String, Object> findTotal(String key, Integer parentId);

    /**
     * 查询一级菜单
     * @param isDeleted
     * @param parentId
     * @return
     */
    List<SysMenu> findLevelOneMenu(byte isDeleted, byte parentId);


    List<MenuDto> findAllMenu(Byte isDeletedNo);

    List<MenuDto> findAllMenuRoles();

    List<SysMenuOperation> findAllOperations(Integer menuId, Integer pageNo);

    Map<String, Object> findOperationTotal(Integer menuId);

    SysMenuOperation findByOperationId(Integer operationId);

    void deleteOperation(Integer operationId);

    Integer addOperation(Integer menuId, String name, String code);

    void updateOperation(Integer id, String name, String code);
}
