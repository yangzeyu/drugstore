package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.entity.Manufacture;

import java.util.List;

@Mapper
@Repository
public interface ManufactureMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Manufacture record);

    int insertSelective(Manufacture record);

    Manufacture selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Manufacture record);

    int updateByPrimaryKey(Manufacture record);

    Manufacture findByName(String name);

    List<Manufacture> findManufacture(@Param("keyword") String keyword);

    List<Manufacture> findAllManufacture();
}
