package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.DrugStoreUser;

import java.util.List;

@Mapper
@Repository
public interface DrugStoreUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DrugStoreUser record);

    int insertSelective(DrugStoreUser record);

    DrugStoreUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DrugStoreUser record);

    int updateByPrimaryKey(DrugStoreUser record);

    List<DrugStoreUser> findUserWithoutDeleted(@Param("userName") String userName,
                                               @Param("status") Byte status);

    List<DrugStoreUser> findStoreUser(@Param("storeId") Integer storeId);

    DrugStoreUser findByUserName(@Param("userName") String userName);

    DrugStoreUser findByStoreId(@Param("drugStoreId") Integer drugStoreId);
}
