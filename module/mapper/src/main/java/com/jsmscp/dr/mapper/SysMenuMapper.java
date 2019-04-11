package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.SysMenu;

import java.util.List;


@Mapper
@Repository
public interface SysMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    List<SysMenu> list(@Param("keyword") String key, @Param("parentId") Integer parentId,
                       @Param("pageNo") Integer pageNo, @Param("pageSize") Byte pageSize,
                       @Param("isDeleted") Byte isDeleted);

    int findCount(@Param("keyword") String key, @Param("parentId") Integer parentId,
                  @Param("isDeleted") Byte isDeleted);

    SysMenu findOne(@Param("menuId") Integer menuId);

    List<SysMenu> findLevelOneMenu(@Param("parentId") byte menuParentId, @Param("isDeleted") byte isDeletedNo);

    List<SysMenu> findAllMenu(@Param("isDeleted") Byte isDeletedNo);
}
