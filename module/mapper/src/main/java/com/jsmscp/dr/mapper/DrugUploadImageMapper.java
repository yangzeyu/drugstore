package com.jsmscp.dr.mapper;

import com.jsmscp.dr.entity.DrugDeliveryItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.DrugUploadImage;

import java.util.List;

@Mapper
@Repository
public interface DrugUploadImageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrugUploadImage record);

    int insertSelective(DrugUploadImage record);

    DrugUploadImage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugUploadImage record);

    int updateByPrimaryKey(DrugUploadImage record);

    List<String> findInvoiceImg(@Param("deliveryId") Integer deliveryId);

    List<String> findDeliverImg(@Param("deliveryId") Integer deliveryId);

    List<Long> findAllItemId();
}
