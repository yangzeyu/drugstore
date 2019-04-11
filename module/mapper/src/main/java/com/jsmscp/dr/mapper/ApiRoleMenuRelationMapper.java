package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.ApiRoleMenuRelation;


@Mapper
@Repository
public interface ApiRoleMenuRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApiRoleMenuRelation record);

    int insertSelective(ApiRoleMenuRelation record);

    ApiRoleMenuRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApiRoleMenuRelation record);

    int updateByPrimaryKey(ApiRoleMenuRelation record);
}
