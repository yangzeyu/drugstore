
package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.DrugDeliveryItem;

import java.util.List;


@Mapper
@Repository
public interface DrugDeliveryItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrugDeliveryItem record);

    int insertSelective(DrugDeliveryItem record);

    DrugDeliveryItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugDeliveryItem record);

    int updateByPrimaryKey(DrugDeliveryItem record);
}
