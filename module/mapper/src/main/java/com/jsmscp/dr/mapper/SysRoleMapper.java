package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.SysRole;

import java.util.List;


@Mapper
@Repository
public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectAll(@Param("isDeleted") Byte isDeleted);

    int findCount(Integer roleId);

    List<SysRole> list(@Param("keyword") String key, @Param("pageNo") Integer pageNo, @Param("pageSize") Byte pageSize,
                       @Param("isDeleted") Byte isDeleted);

    int findAllCount(@Param("keyword") String key, @Param("isDeleted") Byte isDeleted);

    SysRole findOne(Integer roleId);

    List<SysRole> findAllRole();
}
