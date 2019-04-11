package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.SysRoleUserRelation;


@Mapper
@Repository
public interface SysRoleUserRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUserRelation record);

    int insertSelective(SysRoleUserRelation record);

    SysRoleUserRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleUserRelation record);

    int updateByPrimaryKey(SysRoleUserRelation record);
}
