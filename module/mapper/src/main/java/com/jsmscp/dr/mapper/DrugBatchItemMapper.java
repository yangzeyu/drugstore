package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jsmscp.dr.entity.DrugBatchItem;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DrugBatchItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrugBatchItem record);

    int insertSelective(DrugBatchItem record);

    DrugBatchItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugBatchItem record);

    int updateByPrimaryKey(DrugBatchItem record);

    DrugBatchItem findByStoreId(@Param("id") Integer id, @Param("leastStock") Integer leastStock);
}
