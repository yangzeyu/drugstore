package com.jsmscp.dr.mapper.report;

import com.jsmscp.dr.dto.MedicalInsuranceReportDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MedicalInsuranceReportMapper {
    /**
     * 医保进销存统计分析
     * @param threeDirectoryName
     * @param medicalInsuranceCode
     * @param collectLvl
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<MedicalInsuranceReportDto> findAllInsuranceReport(@Param("threeDirectoryName") String threeDirectoryName,
                                                           @Param("medicalInsuranceCode") String medicalInsuranceCode,
                                                           @Param("collectLvl") Byte collectLvl,
                                                           @Param("pageNo") Integer pageNo,
                                                           @Param("pageSize") Integer pageSize);

    Integer findCount(@Param("threeDirectoryName") String threeDirectoryName,
                      @Param("medicalInsuranceCode") String medicalInsuranceCode,
                      @Param("collectLvl") Byte collectLvl);

    /**
     * 医保进销存统计分析导出
     * @param threeDirectoryName
     * @param medicalInsuranceCode
     * @param collectLvl
     * @return
     */
    List<MedicalInsuranceReportDto> exportMedicalInsurance(@Param("threeDirectoryName") String threeDirectoryName,
                                                           @Param("medicalInsuranceCode") String medicalInsuranceCode,
                                                           @Param("collectLvl") Byte collectLvl);
}
