package com.jsmscp.dr.controller.api;

import com.jsmscp.dr.dto.DrugDeliverDto;

import com.jsmscp.dr.util.ExportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.dto.DrugDeliverItemDto;
import com.jsmscp.dr.dto.DrugInvoiceDto;
import com.jsmscp.dr.service.DrugDeliverService;
import com.jsmscp.dr.service.DrugInvoiceService;
import com.jsmscp.dr.util.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/drugDeliver")
public class DrugDeliverController {

    private DrugDeliverService drugDeliverService;
    private DrugInvoiceService drugInvoiceService;

    @Autowired
    public DrugDeliverController(DrugDeliverService drugDeliverService, DrugInvoiceService drugInvoiceService) {
        this.drugDeliverService = drugDeliverService;
        this.drugInvoiceService = drugInvoiceService;
    }

    /**
     * 列表查询进货单
     * @param deliverCode
     * @param storeId
     * @param type
     * @param startTime
     * @param endTime
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list (@RequestParam(value = "deliverCode", required = false) String deliverCode,
                          @RequestParam(value = "storeId", required = false) Integer storeId,
                          @RequestParam(value = "drugName", required = false) String drugName,
                          @RequestParam(value = "type", required = false) Byte type,
                          @RequestParam(value = "startTIme", required = false) String startTime,
                          @RequestParam(value = "endTime", required = false) String endTime,
                          @RequestParam(value = "pageNo") Integer pageNo) {
        HashMap<String, Object> map = drugDeliverService.list(deliverCode, storeId, drugName, type, startTime,
                                                                endTime, pageNo);
        return Response.buildSuccess(map);
    }

    /**
     * 查看入库详情
     * @param deliverId
     * @return
     */
    @RequestMapping(value = "findDrugDeliverItem", method = RequestMethod.GET)
    public Response findDrugDeliverItem (@RequestParam(value = "deliverId") Integer deliverId,
                                         @RequestParam(value = "storeId") Integer storeId) {
        List<DrugDeliverItemDto> list = drugDeliverService.findDrugDeliverItem(deliverId, storeId);
        return Response.buildSuccess(list);
    }


    /**
     * 查看发票
     * @param deliverId
     * @return
     */
    @RequestMapping(value = "findInvoiceItem", method = RequestMethod.GET)
    public Response findInvoiceItem (@RequestParam(value = "deliverId") Integer deliverId) {
        List<DrugInvoiceDto> list = drugInvoiceService.findInvoiceItem(deliverId);
        return Response.buildSuccess(list);
    }

    /**
     * 导出入库信息
     * @param deliverCode
     * @param storeId
     * @param drugName
     * @param type
     * @param startTime
     * @param endTime
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "export", method = RequestMethod.GET)
    public void export(@RequestParam(value = "deliverCode", required = false) String deliverCode,
                       @RequestParam(value = "storeId", required = false) Integer storeId,
                       @RequestParam(value = "drugName", required = false) String drugName,
                       @RequestParam(value = "type", required = false) Byte type,
                       @RequestParam(value = "startTIme", required = false) String startTime,
                       @RequestParam(value = "endTime", required = false) String endTime,
                       HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        String fileName = "入库信息管理"  + ".xls";
        String sheetName = "入库信息管理";
        List<DrugDeliverDto> list = drugDeliverService.exportDrugDeliver(deliverCode, storeId, drugName, type,
                startTime, endTime);
        List<String> titles = new ArrayList<>();
        titles.add("进货单号");
        titles.add("金额");
        titles.add("日期");
        titles.add("发票状态");
        titles.add("类型");
        titles.add("匹配状态");
        titles.add("医保卡姓名");
        titles.add("社会保险号");
        titles.add("出库单号");
        titles.add("药店");
        ExportExcelUtil.exportStockDeliver(fileName, sheetName, titles, list, request, response);
    }

}
