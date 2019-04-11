package com.jsmscp.dr.service.report;



import com.jsmscp.dr.dto.MedicalInsuranceReportDto;

import java.util.HashMap;
import java.util.List;

public interface MedicalInsuranceReportService {

    HashMap<String, Object> findAllInsuranceReport(String threeDirectoryName, String medicalInsuranceCode,
                                   Byte collectLvl, Integer pageNo);

    List<MedicalInsuranceReportDto> exportMedicalInsurance(String threeDirectoryName, String medicalInsuranceCode,
                                                           Byte collectLvl);
}
