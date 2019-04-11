package com.jsmscp.dr.service.report.impl;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.MedicalInsuranceReportDto;
import com.jsmscp.dr.mapper.report.MedicalInsuranceReportMapper;
import com.jsmscp.dr.service.report.MedicalInsuranceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MedicalInsuranceReportServiceImpl implements MedicalInsuranceReportService {

    private MedicalInsuranceReportMapper medicalInsuranceReportMapper;

    @Autowired
    public MedicalInsuranceReportServiceImpl(MedicalInsuranceReportMapper medicalInsuranceReportMapper) {
        this.medicalInsuranceReportMapper = medicalInsuranceReportMapper;
    }


    /**
     * 医保进销存统计分析
     * @return
     */
    @Override
    public HashMap<String, Object> findAllInsuranceReport(String threeDirectoryName, String medicalInsuranceCode,
                                                          Byte collectLvl, Integer pageNo) {
        HashMap<String, Object> map  = new HashMap<>();
        Integer pageSize = Constant.DEFAULT_PAGE_SIZE;
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        pageNo = (pageNo - 1) * pageSize;
        List<MedicalInsuranceReportDto> list =  medicalInsuranceReportMapper.findAllInsuranceReport(threeDirectoryName,
                medicalInsuranceCode, collectLvl, pageNo, pageSize);
        Integer count = medicalInsuranceReportMapper.findCount(threeDirectoryName, medicalInsuranceCode, collectLvl);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        map.put("list", list);
        return map;
    }


    /**
     * 医保目录进销存统计分析导出
     * @param threeDirectoryName
     * @param medicalInsuranceCode
     * @param collectLvl
     * @return
     */
    @Override
    public List<MedicalInsuranceReportDto> exportMedicalInsurance(String threeDirectoryName, String medicalInsuranceCode,
                                                                  Byte collectLvl) {
            List<MedicalInsuranceReportDto> list = medicalInsuranceReportMapper.exportMedicalInsurance(threeDirectoryName,
                    medicalInsuranceCode, collectLvl);
            return list;
    }
}
