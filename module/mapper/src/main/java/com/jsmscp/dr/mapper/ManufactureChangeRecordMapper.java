package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.ManufactureChangeRecord;
@Mapper
@Repository
public interface ManufactureChangeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManufactureChangeRecord record);

    int insertSelective(ManufactureChangeRecord record);

    ManufactureChangeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManufactureChangeRecord record);

    int updateByPrimaryKey(ManufactureChangeRecord record);
}
