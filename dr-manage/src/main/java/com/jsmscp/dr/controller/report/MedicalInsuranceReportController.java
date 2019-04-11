package com.jsmscp.dr.controller.report;

import com.jsmscp.dr.dto.MedicalInsuranceReportDto;
import com.jsmscp.dr.service.report.MedicalInsuranceReportService;
import com.jsmscp.dr.util.ExportExcelUtil;
import com.jsmscp.dr.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/report/medicalInsurance")
public class MedicalInsuranceReportController {
    private static final Logger log = LoggerFactory.getLogger(MedicalInsuranceReportController.class);

    private MedicalInsuranceReportService medicalInsuranceReportService;

    @Autowired
    public MedicalInsuranceReportController(MedicalInsuranceReportService medicalInsuranceReportService) {
        this.medicalInsuranceReportService = medicalInsuranceReportService;
    }


    /**
     * 医保进销存统计分析
     * @param threeDirectoryName
     * @param medicalInsuranceCode
     * @param collectLvl
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "findAllInsuranceReport", method = RequestMethod.GET)
    public Response findAllInsuranceReport(@RequestParam(value = "threeDirectoryName", required = false) String threeDirectoryName,
                                           @RequestParam(value = "medicalInsuranceCode", required = false) String medicalInsuranceCode,
                                           @RequestParam(value = "collectLvl", required = false) Byte collectLvl,
                                           @RequestParam(value = "pageNo") Integer pageNo) {
            HashMap<String, Object> map = medicalInsuranceReportService.findAllInsuranceReport(threeDirectoryName,
                    medicalInsuranceCode, collectLvl, pageNo);
            return Response.buildSuccess(map);

    }

    /**
     * 医保进销存统计分析数据导出
     * @param threeDirectoryName
     * @param medicalInsuranceCode
     * @param collectLvl
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping(value = "export", method = RequestMethod.GET)
    public void export(@RequestParam(value = "threeDirectoryName", required = false) String threeDirectoryName,
                       @RequestParam(value = "medicalInsuranceCode", required = false) String medicalInsuranceCode,
                       @RequestParam(value = "collectLvl", required = false) Byte collectLvl,
                       HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        String fileName = "医保进销存统计分析"  + ".xls";
        String sheetName = "医保进销存统计分析";
        List<MedicalInsuranceReportDto> list = medicalInsuranceReportService.exportMedicalInsurance(threeDirectoryName,
                medicalInsuranceCode, collectLvl);
        List<String> titles = new ArrayList<>();
        titles.add("医保编码");
        titles.add("医保目录名称");
        titles.add("药店");
        titles.add("药品编码");
        titles.add("通用名");
        titles.add("剂型");
        titles.add("厂家");
        titles.add("规格");
        titles.add("出库数量");
        titles.add("收费项目等级");
        ExportExcelUtil.exportMedicalInsurance(fileName, sheetName, titles, list, request, response);
    }
}
