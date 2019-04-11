package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.DrugStockOutItem;
@Mapper
@Repository
public interface DrugStockOutItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrugStockOutItem record);

    int insertSelective(DrugStockOutItem record);

    DrugStockOutItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugStockOutItem record);

    int updateByPrimaryKey(DrugStockOutItem record);

    void compareDrug(@Param("drugId") Long drugId, @Param("id") Long id, @Param("drugCode") String drugCode);

    void delDrug(@Param("outId") Long outId, @Param("drugCode") String drugCode);
}
