package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.ApiRoleUserRelation;

@Mapper
@Repository
public interface ApiRoleUserRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApiRoleUserRelation record);

    int insertSelective(ApiRoleUserRelation record);

    ApiRoleUserRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApiRoleUserRelation record);

    int updateByPrimaryKey(ApiRoleUserRelation record);
}
