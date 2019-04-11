package com.jsmscp.dr.mapper;

import com.jsmscp.dr.dto.UploadPriceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.dto.DrugDeliverDto;
import com.jsmscp.dr.dto.DrugDeliverItemDto;
import com.jsmscp.dr.entity.DrugDelivery;

import java.util.List;

@Mapper
@Repository
public interface DrugDeliveryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrugDelivery record);

    int insertSelective(DrugDelivery record);

    DrugDelivery selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugDelivery record);

    int updateByPrimaryKey(DrugDelivery record);

    DrugDelivery findByCode(@Param("deliverCode") String deliverCode, @Param("storeId") Long storeId);

    List<DrugDeliverDto> findDrugDeliver(@Param("deliverCode") String deliverCode, @Param("storeId") Integer storeId,
                                         @Param("drugName") String drugName, @Param("type") Byte type,
                                         @Param("startTime") String startTime, @Param("endTime") String endTime,
                                         @Param("pageNo") Integer pageNo, @Param("pageSize") Byte pageSize);

    Integer findCount(@Param("deliverCode") String deliverCode, @Param("storeId") Integer storeId,
                      @Param("drugName") String drugName, @Param("type") Byte type,
                      @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<DrugDeliverItemDto> findDrugDeliverItem(@Param("deliverId") Integer deliverId);

    List<DrugDeliverItemDto> findDeliverDetail(@Param("deliverId") Integer deliverId);

    List<DrugDeliverItemDto> findByDeliverCode(@Param("deliverCode") String deliverCode,
                                               @Param("storeId") Integer storeId);

    void compareDrug(@Param("drugCode") String drugCode, @Param("id") Long id, @Param("drugId") Long drugId);

    List<DrugDelivery> findNoDrugId(@Param("storeId") Integer storeId);

    List<DrugDelivery> findByDrugId(@Param("drugCode") String drugCode, @Param("storeId") Integer storeId);

    void delDrug(@Param("drugCode") String drugCode, @Param("deliverId") Long deliverId);

    List<DrugDeliverDto> findIsPairCode(@Param("deliverIds") List<Integer> deliverIds);

    List<DrugDeliverDto> exportDrugDeliver(@Param("deliverCode") String deliverCode, @Param("storeId") Integer storeId,
                                           @Param("drugName") String drugName, @Param("type") Byte type,
                                           @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<DrugDeliverDto> findByStoreId(@Param("storeId") Integer storeId);

    List<UploadPriceDto> findUploadPrice(@Param("storeId") String storeId, @Param("startTime") String startTime,
                                         @Param("endTime") String endTime);
}
