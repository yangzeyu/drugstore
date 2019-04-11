package com.jsmscp.dr.mapper;

import com.jsmscp.dr.entity.MedicalInsurance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MedicalInsuranceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MedicalInsurance record);

    int insertSelective(MedicalInsurance record);

    MedicalInsurance selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MedicalInsurance record);

    int updateByPrimaryKey(MedicalInsurance record);

    List<MedicalInsurance> findMedicalInsurance(@Param("keyword") String keyword,
                                                   @Param("collectType") String collectType,
                                                   @Param("collectLvl") String collectLvl,
                                                   @Param("pageNo") Integer pageNo,
                                                   @Param("pageSize") Byte pageSize);

    List<MedicalInsurance> findAllGrade();

    int findMedicalInsuranceCount(@Param("keyword") String keyword,
                                  @Param("collectType") String collectType,
                                  @Param("collectLvl") String collectLvl);

    int findByCode(@Param("medicalInsuranceCode") String medicalInsuranceCode);

    List<MedicalInsurance> findAllMedicalInsuranceCode(@Param("keyword") String keyword);

    List<MedicalInsurance> findAllInsurance();

    List<MedicalInsurance> findInsuranceCompare(@Param("keyword") String keyword,
                                                @Param("collectType") String collectType,
                                       @Param("collectLvl") String collectLvl);

    List<MedicalInsurance> findAllInsuranceByKey(@Param("keyword") String keyword, @Param("zeroSize") Integer zeroSize,
                                                 @Param("dropDownListSize") Integer dropDownListSize);
}
