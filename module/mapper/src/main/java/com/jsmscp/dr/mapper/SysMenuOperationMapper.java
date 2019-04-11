package com.jsmscp.dr.mapper;

import com.jsmscp.dr.entity.SysMenuOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysMenuOperationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysMenuOperation record);

    int insertSelective(SysMenuOperation record);

    SysMenuOperation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenuOperation record);

    int updateByPrimaryKey(SysMenuOperation record);

    List<SysMenuOperation> findAllOperation();

    List<SysMenuOperation> findMenuOperation(@Param("pageNo") Integer pageNo,
                                             @Param("pageSize") Byte pageSize, @Param("menuId") Integer menuId);

    int findOperationCount(Integer menuId);
}
