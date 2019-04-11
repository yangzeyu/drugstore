package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.dto.DrugStockOutDto;
import com.jsmscp.dr.dto.DrugStockOutItemDto;
import com.jsmscp.dr.entity.DrugStockOut;

import java.util.List;

@Mapper
@Repository
public interface DrugStockOutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DrugStockOut record);

    int insertSelective(DrugStockOut record);

    DrugStockOut selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DrugStockOut record);

    int updateByPrimaryKey(DrugStockOut record);

    DrugStockOut findByOutNo(@Param("outNo") String outNo, @Param("storeId") Integer storeId);

    List<DrugStockOutDto> findDrugStockOut(@Param("outNo") String outNo, @Param("drugStoreId") Integer drugStoreId,
                                           @Param("type") Byte type, @Param("drugName") String drugName,
                                           @Param("payType") Integer payType, @Param("startTime") String startTime,
                                           @Param("endTime") String endTime, @Param("pageNo") Integer pageNo,
                                           @Param("pageSize") Byte pageSize);

    Integer findCount(@Param("outNo") String outNo, @Param("drugStoreId") Integer drugStoreId,
                      @Param("type") Byte type, @Param("drugName") String drugName, @Param("payType") Integer payType,
                      @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<DrugStockOutItemDto> findDrugStockOutItem(@Param("storeOutId") String storeOutId);

    List<DrugStockOut> findNoDrugId(@Param("storeId") Integer storeId);

    List<DrugStockOutItemDto> findByStockOutNo(@Param("outNo") String outNo, @Param("storeId") Integer storeId);

    List<DrugStockOut> findByDrugId(@Param("drugCode") String drugCode, @Param("storeId") Integer storeId);

    List<DrugStockOutDto> findIsPairCode(@Param("stockOutIds") List<Integer> stockOutIds);

    List<DrugStockOutDto> exportStockOut(@Param("outNo") String outNo, @Param("drugStoreId") Integer drugStoreId,
                                         @Param("type") Byte type, @Param("payType") Integer payType,
                                         @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<DrugStockOutDto> findByStoreId(@Param("storeId") Integer storeId);
}
