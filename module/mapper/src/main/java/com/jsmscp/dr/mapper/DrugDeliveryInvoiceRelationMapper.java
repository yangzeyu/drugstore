package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.dto.DeliverInvoiceDto;
import com.jsmscp.dr.entity.DrugDeliveryInvoiceRelation;

import java.util.List;

@Mapper
@Repository
public interface DrugDeliveryInvoiceRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrugDeliveryInvoiceRelation record);

    int insertSelective(DrugDeliveryInvoiceRelation record);

    DrugDeliveryInvoiceRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugDeliveryInvoiceRelation record);

    int updateByPrimaryKey(DrugDeliveryInvoiceRelation record);

    List<DeliverInvoiceDto> findDeliverInvoice(@Param("storeId") Integer storeId,
                                               @Param("keyword") String keyword, @Param("startTime") String startTime,
                                               @Param("endTime") String endTime, @Param("pageNo") Integer pageNo,
                                               @Param("pageSize") Byte pageSize);

    int findCount(@Param("storeId") Integer storeId, @Param("keyword") String keyword,
                  @Param("startTime") String startTime, @Param("endTime") String endTime);
}
