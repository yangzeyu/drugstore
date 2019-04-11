package com.jsmscp.dr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jsmscp.dr.dto.DrugBaseDto;
import com.jsmscp.dr.entity.DrugBase;

import java.util.List;

@Mapper
@Repository
public interface DrugBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrugBase record);

    int insertSelective(DrugBase record);

    DrugBase selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugBase record);

    int updateByPrimaryKey(DrugBase record);

    List<DrugBaseDto> findDrugBase(@Param("keyword") String keyword, @Param("permissionNum") String permissionNum,
                                   @Param("type") Byte type, @Param("manufactureId") Integer manufactureId,
                                   @Param("pageNo") Integer pageNo, @Param("pageSize") Byte pageSize,
                                   @Param("medicalInsuranceId") Long medicalInsuranceId);

    int findCount(@Param("keyword") String keyword, @Param("permissionNum") String permissionNum,
                  @Param("type") Byte type, @Param("manufactureId") Integer manufactureId,
                  @Param("medicalInsuranceId") Long medicalInsuranceId);

    DrugBase findDrug(@Param("goodName") String goodName, @Param("dosageForm") String dosageForm,
                      @Param("spec") String spec, @Param("unit") String unit,
                      @Param("manufactureId") Integer manufactureId);

    List<DrugBase> findAllDrug();

    DrugBase findByInsuranceCode(String insuranceCode);

    List<DrugBaseDto> findAllDrugBase(@Param("keyword") String keyword, @Param("manufactureId") Integer manufactureId);

    int findAllDrugBaseCount(@Param("keyword") String keyword, @Param("manufactureId") Integer manufactureId);

    DrugBaseDto selectByDrugId(@Param("drugId") Long drugId);

    int findByCode(@Param("platFormCode") String platFormCode);

    //查询所有未对码的信息
    List<DrugBaseDto> findNoInsuranceId();

    List<DrugBase> selectDrugInsurance(@Param("keyword") String keyword,
                                           @Param("manufactureId") Integer manufactureId);

    void compareInsurance(@Param("medicalInsuranceId") Long medicalInsuranceId, @Param("drugId") Long drugId);

    void delCompare(@Param("drugId") Long drugId);

    List<String> findAllDrugByKey(@Param("keyword") String keyword, @Param("zeroNo") Integer zeroNo,
                                  @Param("dropDownListSize") Integer dropDownListSize);

    List<DrugBase> selectByInsuranceId(@Param("id") Long id);

}
