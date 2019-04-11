package com.jsmscp.dr.mapper;

import com.jsmscp.dr.dto.StoreOutInReportDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.dto.DrugCatalogDto;
import com.jsmscp.dr.entity.DrugStore;

import java.util.List;

@Mapper
@Repository
public interface DrugStoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DrugStore record);

    int insertSelective(DrugStore record);

    DrugStore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DrugStore record);

    int updateByPrimaryKey(DrugStore record);

    DrugStore findByAppCode(String appCode);

    List<DrugStore> findAllDrugStore(@Param("keyword") String keyword, @Param("status") Byte status);

    String findMaxCode();

    DrugStore findByName(@Param("storeName") String storeName, @Param("code") String code);

    List<DrugCatalogDto> findDrugStore(@Param("keyword") String keyword, @Param("status") Byte status,
                                     @Param("startTime") String startTime, @Param("endTime") String endTime,
                                     @Param("pageNo") Integer pageNo, @Param("pageSize") Byte pageSize);

    int findCount(@Param("keyword") String keyword, @Param("status") Byte status,
                  @Param("startTime") String startTime, @Param("endTime") String endTime);

    DrugStore findOne(@Param("drugStoreId") String drugStoreId);

    List<StoreOutInReportDto> findStoreOutIn(@Param("storeId") Integer storeId, @Param("goodName") String goodName,
                                             @Param("startTime") String startTime, @Param("endTime") String endTIme,
                                             @Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    int findStoreOutInCount(@Param("storeId") Integer storeId, @Param("goodName") String goodName,
                            @Param("startTime") String startTime, @Param("endTime") String endTIme);

    List<StoreOutInReportDto> exportStore(@Param("storeId") Integer storeId,
                                          @Param("goodName") String goodName,
                                          @Param("startTime") String startTime,
                                          @Param("endTime") String endTime);
}
