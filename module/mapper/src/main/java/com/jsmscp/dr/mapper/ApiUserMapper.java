package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.ApiUser;
import com.jsmscp.dr.entity.SysMenu;

import java.util.List;

@Mapper
@Repository
public interface ApiUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApiUser record);

    int insertSelective(ApiUser record);

    ApiUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApiUser record);

    int updateByPrimaryKey(ApiUser record);

    List<ApiUser> list(@Param("pageNo") Integer pageNo, @Param("keyword") String keyword,
                       @Param("roleId") Integer roleId, @Param("partyId") Integer partyId,
                       @Param("status") Integer status, @Param("pageSize") Byte pageSize);

    ApiUser getApiUserByName();

    int findCount(String keyword, Integer roleId, Integer partyId, Integer status);

    List<ApiUser> findUserWithoutDeleted(@Param("userName") String username,
                                         @Param("status") Byte status);

    List<SysMenu> findMenuByUserId(@Param("id") Integer id, @Param("isDeleted") Byte isDeleted);

    ApiUser selectByName(String userName);

}
