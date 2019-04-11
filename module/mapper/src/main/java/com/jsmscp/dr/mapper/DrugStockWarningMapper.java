package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.dto.DrugStockWarningDto;
import com.jsmscp.dr.entity.DrugStockWarning;

import java.util.List;

@Mapper
@Repository
public interface DrugStockWarningMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrugStockWarning record);

    int insertSelective(DrugStockWarning record);

    DrugStockWarning selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugStockWarning record);

    int updateByPrimaryKey(DrugStockWarning record);

    List<DrugStockWarningDto> findAllWarning(@Param("stockWarningNotRead") Byte stockWarningNotRead,
                                             @Param("stockWarningRead") Byte stockWarningRead);
}
