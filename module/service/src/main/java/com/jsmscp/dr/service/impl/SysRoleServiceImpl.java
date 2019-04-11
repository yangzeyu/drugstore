package com.jsmscp.dr.service.impl;

import com.jsmscp.dr.entity.SysMenuOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.entity.SysMenu;
import com.jsmscp.dr.entity.SysRole;
import com.jsmscp.dr.entity.SysRoleMenuRelation;
import com.jsmscp.dr.mapper.SysRoleMapper;
import com.jsmscp.dr.mapper.SysRoleMenuRelationMapper;
import com.jsmscp.dr.service.SysRoleService;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    private SysRoleMapper mapper;
    private SysRoleMenuRelationMapper sysRoleMenuRelationMapper;

    @Autowired
    public SysRoleServiceImpl(SysRoleMapper mapper, SysRoleMenuRelationMapper sysRoleMenuRelationMapper) {
        this.mapper = mapper;
        this.sysRoleMenuRelationMapper = sysRoleMenuRelationMapper;
    }

    /**
     * 添加角色
     * @param name
     * @param code
     * @return
     */
    @Override
    public Integer addRole(String name, String code) {
        SysRole role = new SysRole();
        role.setCode(code);
        role.setName(name);
        role.setIsDeleted(Constant.IS_DELETED_NO);
        role.setCreateAt(new Date());
        mapper.insert(role);
        return role.getId();
    }

    /**
     * 修改角色下的菜单
     * @param menuIds
     * @param operationIds
     * @param roleId
     */
    @Override
    @Transactional
    public void editRoleMenu(String menuIds, String operationIds, Integer roleId) {
        sysRoleMenuRelationMapper.deleteByRoleId(roleId);
        List<String> ids = Arrays.asList(menuIds.split(","));
        List<String> oids = Arrays.asList(operationIds.split(","));
        for (String id : ids) {
            Integer menuId = Integer.parseInt(id);
            SysRoleMenuRelation relation = new SysRoleMenuRelation();
            relation.setCreateAt(new Date());
            relation.setMenuId(menuId);
            relation.setRoleId(roleId);
            relation.setUpdateAt(new Date());
            relation.setMenuType(Constant.MENU_TYPE_MENU);
            sysRoleMenuRelationMapper.insert(relation);
        }
        for (String id : oids) {
            Integer menuId = Integer.parseInt(id);
            SysRoleMenuRelation relation = new SysRoleMenuRelation();
            relation.setCreateAt(new Date());
            relation.setMenuId(menuId);
            relation.setRoleId(roleId);
            relation.setUpdateAt(new Date());
            relation.setMenuType(Constant.MENU_TYPE_OPERATION);
            sysRoleMenuRelationMapper.insert(relation);
        }
    }


    /**
     * 查询角色列表数量
     * @param key
     * @return
     */
    @Override
    public HashMap<String, Object> findAllCount(String key) {
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        HashMap<String, Object> map = new HashMap<>();
        int count = mapper.findAllCount(key, Constant.IS_DELETED_NO);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        return map;
    }

    /**
     * 根据角色id查询角色
     * @param roleId
     * @return
     */
    @Override
    public SysRole findOne(Integer roleId) {
       SysRole role = mapper.findOne(roleId);
        return role;
    }

    /**
     * 根据角色查询菜单
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenu> findMenuByRole(Integer roleId) {
        List<SysMenu> menu = sysRoleMenuRelationMapper.findMenuByRoleId(roleId);
        return menu;
    }

    /**
     * 根据角色查询页面按钮
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenuOperation> findOperationByRole(Integer roleId) {
        return sysRoleMenuRelationMapper.findOperationByRole(roleId);
    }

    /**
     * 查询角色列表
     * @return
     */
    @Override
    public List<SysRole> findAllRole() {
        List<SysRole> list = mapper.findAllRole();
        return list;
    }

    /**
     * 修改角色信息
     * @param roleId
     * @param roleName
     * @param roleCode
     */
    @Override
    public void edit(Integer roleId, String roleName, String roleCode) {
        SysRole role = new SysRole();
        role.setId(roleId);
        role.setCode(roleCode);
        role.setName(roleName);
        role.setUpdateAt(new Date());
        mapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 根据状态查询所有角色
     * @param isDeleted
     * @return
     */
    @Override
    public List<SysRole> findAllRoles(Byte isDeleted) {
        List<SysRole> list = mapper.selectAll(isDeleted);
        return list;
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @Override
    public boolean delete(Integer roleId) {
        int count = mapper.findCount(roleId);
        if (count == 0) {
            SysRole list = mapper.selectByPrimaryKey(roleId);
            list.setIsDeleted(Constant.IS_DELETED_YES);
            mapper.updateByPrimaryKeySelective(list);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 列表查询角色信息
     * @param key
     * @param pageNo
     * @return
     */
    @Override
    public List<SysRole> list(String key, Integer pageNo) {
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        List<SysRole> list =  mapper.list(key, pageNo, pageSize, Constant.IS_DELETED_NO);
        return list;
    }


}
