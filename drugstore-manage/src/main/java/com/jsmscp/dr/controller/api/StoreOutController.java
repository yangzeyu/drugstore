package com.jsmscp.dr.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.dto.DrugStockOutItemDto;
import com.jsmscp.dr.service.DrugStockOutService;
import com.jsmscp.dr.util.Response;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/DrugStockOut")
public class StoreOutController {

    private DrugStockOutService drugStockOutService;

    @Autowired
    public StoreOutController(DrugStockOutService drugStockOutService) {
        this.drugStockOutService = drugStockOutService;
    }

    /**
     * 查询药品出库记录列表
     * @param outNo
     * @param drugStoreId
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "findDrugStockOut", method = RequestMethod.GET)
    public Response list (@RequestParam(value = "outNo", required = false) String outNo,
                          @RequestParam(value = "drugStoreId") Integer drugStoreId,
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
     * 查询药品出库记录列表
     * @return
     */
    @RequestMapping(value = "findDrugStockOutItem", method = RequestMethod.GET)
    public Response list (@RequestParam(value = "storeOutId", required = false) String storeOutId,
                          @RequestParam(value = "storeId") Integer storeId) {
        List<DrugStockOutItemDto> list = drugStockOutService.findStoreItem(storeOutId, storeId);
        return Response.buildSuccess(list);
    }

}
