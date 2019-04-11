package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.SysMenu;
import com.jsmscp.dr.entity.SysUser;

import java.util.List;


@Mapper
@Repository
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    List<SysUser> list(@Param("pageSize") Byte pageSize, @Param("pageNo") Integer pageNo, @Param("keyword") String key,
                       @Param("roleId") Integer roleId, @Param("status") Integer status,
                       @Param("isDeleted") Byte isDeleted);

    int findCount(@Param("keyword") String key, @Param("roleId") Integer roleId, @Param("status") Integer status,
                  @Param("isDeleted") Byte isDeleted);

    List<SysUser> findUserWithoutDeleted(@Param("userName") String username,
                                         @Param("isDeleted") Byte isDeleted);

    List<SysMenu> findMenuByUserId(@Param("id") Integer id, @Param("isDeleted") Byte isDeletedNo);

    List<String> findAllRoles(Integer userId);

    SysUser findByUserName(@Param("userName") String userName);
}
