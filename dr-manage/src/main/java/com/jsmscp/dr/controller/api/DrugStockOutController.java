package com.jsmscp.dr.controller.api;


import com.jsmscp.dr.dto.DrugStockOutDto;
import com.jsmscp.dr.util.ExportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.dto.DrugStockOutItemDto;
import com.jsmscp.dr.service.DrugStockOutService;
import com.jsmscp.dr.util.Response;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/DrugStockOut")
public class DrugStockOutController {

    private DrugStockOutService drugStockOutService;

    @Autowired
    public DrugStockOutController(DrugStockOutService drugStockOutService) {
        this.drugStockOutService = drugStockOutService;
    }


    /**
     * 查询药品出库记录列表
     * @param outNo
     * @param drugStoreId
     * @param startTime
     * @param endTime
     * @param payType
     * @return
     */
    @RequestMapping(value = "findDrugStockOut", method = RequestMethod.GET)
    public Response list (@RequestParam(value = "outNo", required = false) String outNo,
                          @RequestParam(value = "drugStoreId", required = false) Integer drugStoreId,
                          @RequestParam(value = "type", required = false) Byte type,
                          @RequestParam(value = "drugName", required = false) String drugName,
                          @RequestParam(value = "payType", required = false) Integer payType,
                          @RequestParam(value = "startTime", required = false) String startTime,
                          @RequestParam(value = "endTime", required = false) String endTime,
                          @RequestParam(value = "pageNo") Integer pageNo) {
        HashMap<String, Object> map = drugStockOutService.findDrugStockOut(outNo, drugStoreId, type, drugName, payType,
                startTime, endTime, pageNo);
        return Response.buildSuccess(map);
    }
    /**
     * 查询药品出库记录详情
     * @return
     */
    @RequestMapping(value = "findDrugStockOutItem", method = RequestMethod.GET)
    public Response list (@RequestParam(value = "storeOutId", required = false) String storeOutId,
                          @RequestParam(value = "storeId") Integer storeId) {
        List<DrugStockOutItemDto> list = drugStockOutService.findDrugStockOutItem(storeOutId, storeId);
        return Response.buildSuccess(list);
    }


    /**
     * 出库导出
     * @return
     */
    @RequestMapping(value = "export", method = RequestMethod.GET)
    public void export(@RequestParam(value = "outNo", required = false) String outNo,
                       @RequestParam(value = "drugStoreId", required = false) Integer drugStoreId,
                       @RequestParam(value = "type", required = false) Byte type,
                       @RequestParam(value = "payType", required = false) Integer payType,
                       @RequestParam(value = "startTime", required = false) String startTime,
                       @RequestParam(value = "endTime", required = false) String endTime,
                       HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        String fileName = "出库信息管理"  + ".xls";
        String sheetName = "出库信息管理";
        List<DrugStockOutDto> list = drugStockOutService.exportStockOut(outNo, drugStoreId, type, payType, startTime,
                endTime);
        List<String> titles = new ArrayList<>();
        titles.add("出库单号");
        titles.add("药店");
        titles.add("出库金额");
        titles.add("记账金额");
        titles.add("付款方式");
        titles.add("交易方名称");
        titles.add("出库日期");
        titles.add("类型");
        titles.add("匹配状态");
        ExportExcelUtil.exportStockOut(fileName, sheetName, titles, list, request, response);
    }
}
