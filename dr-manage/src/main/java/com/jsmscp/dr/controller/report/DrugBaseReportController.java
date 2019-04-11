package com.jsmscp.dr.controller.report;

import com.jsmscp.dr.service.report.DrugBaseReportService;
import com.jsmscp.dr.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/report/drugBase")
public class DrugBaseReportController {
   private static final Logger log = LoggerFactory.getLogger(DrugBaseReportController.class);

    private DrugBaseReportService drugBaseReportService;

    @Autowired
    public DrugBaseReportController(DrugBaseReportService drugBaseReportService) {
        this.drugBaseReportService = drugBaseReportService;
    }

    /**
     * 药品信息查询分析
     * @param goodName
     * @param manufactureId
     * @param platFormCode
     * @param startTime
     * @param endTime
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "findDrugBaseReport", method = RequestMethod.GET)
    public Response findDrugBaseReport(@RequestParam(value = "goodName", required = false) String goodName,
                                       @RequestParam(value = "manufactureId", required = false) Long manufactureId,
                                       @RequestParam(value = "platFormCode", required = false) String platFormCode,
                                       @RequestParam(value = "startTime", required = false) String startTime,
                                       @RequestParam(value = "endTime", required = false) String endTime,
                                       @RequestParam(value = "pageNo") Integer pageNo) {

        HashMap<String, Object> map = drugBaseReportService.findDrugBaseReport(goodName, manufactureId, platFormCode,
                startTime, endTime, pageNo);
        return  Response.buildSuccess(map);



    }
}
