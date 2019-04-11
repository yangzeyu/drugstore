package com.jsmscp.dr.mapper;

import com.jsmscp.dr.entity.SysMenuOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.SysRoleMenuRelation;
import com.jsmscp.dr.entity.SysMenu;

import java.util.List;


@Mapper
@Repository
public interface SysRoleMenuRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleMenuRelation record);

    int insertSelective(SysRoleMenuRelation record);

    SysRoleMenuRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleMenuRelation record);

    int updateByPrimaryKey(SysRoleMenuRelation record);

    List<SysMenu> findMenuByRoleId(@Param("roleId") Integer roleId);

    List<SysMenuOperation> findOperationByRole(Integer roleId);

    void deleteByRoleId(@Param("roleId") Integer roleId);
}
