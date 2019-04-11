package com.jsmscp.dr.controller.report;

import com.jsmscp.dr.dto.StoreOutInReportDto;
import com.jsmscp.dr.service.DrugStoreService;
import com.jsmscp.dr.util.ExportExcelUtil;
import com.jsmscp.dr.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/report/storeOutIn")
public class StoreOutInReportController {

    private static final Logger LOG = LoggerFactory.getLogger(StoreOutInReportController.class);

    /*药店进销存统计分析*/

    private DrugStoreService drugStoreService;

    @Autowired
    public StoreOutInReportController(DrugStoreService drugStoreService) {
        this.drugStoreService = drugStoreService;
    }



    @GetMapping("findStoreOutIn")
    public Response findStoreOutIn(@RequestParam(value = "storeId") Integer storeId,
                                   @RequestParam(value = "goodName", required = false) String goodName,
                                   @RequestParam(value = "startTime", required = false) String startTime,
                                   @RequestParam(value = "endTIme", required = false) String endTIme,
                                   @RequestParam(value = "pageNo") Integer pageNo) {
        HashMap<String, Object> map = drugStoreService.findStoreOutIn(storeId, goodName, startTime, endTIme, pageNo);
        return Response.buildSuccess(map);
    }


    /**
     * 药店进销存统计分析
     */
    @RequestMapping(value = "export", method = RequestMethod.GET)
    public void export(@RequestParam(value = "storeId") Integer storeId,
                       @RequestParam(value = "goodName", required = false) String goodName,
                       @RequestParam(value = "startTime", required = false) String startTime,
                       @RequestParam(value = "endTIme", required = false) String endTime,
                       HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        String fileName = "药店进销存统计分析"  + ".xls";
        String sheetName = "药店进销存统计分析";
        List<StoreOutInReportDto> list = drugStoreService.exportStore(storeId, goodName, startTime, endTime);
        List<String> titles = new ArrayList<>();
        titles.add("药店名称");
        titles.add("通用名");
        titles.add("首营登记");
        titles.add("规格");
        titles.add("剂型");
        titles.add("厂家");
        titles.add("入库数量");
        titles.add("出库数量");
        titles.add("校验库存");
        titles.add("当前库存");
        ExportExcelUtil.exportStore(fileName, sheetName, titles, list, request, response);
    }



}
