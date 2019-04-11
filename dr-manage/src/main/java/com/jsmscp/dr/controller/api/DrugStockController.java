package com.jsmscp.dr.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.dto.DrugDeliverItemDto;
import com.jsmscp.dr.dto.DrugStockOutItemDto;
import com.jsmscp.dr.service.DrugDeliveryItemService;
import com.jsmscp.dr.service.DrugStockOutService;
import com.jsmscp.dr.service.DrugStockService;
import com.jsmscp.dr.util.Response;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/drugStock")
public class DrugStockController {

    private DrugStockService drugStockService;

    private DrugDeliveryItemService drugDeliveryItemService;

    private DrugStockOutService drugStockOutService;


    @Autowired
    public DrugStockController(DrugStockService drugStockService, DrugDeliveryItemService drugDeliveryItemService,
                               DrugStockOutService drugStockOutService) {
        this.drugStockService = drugStockService;
        this.drugDeliveryItemService = drugDeliveryItemService;
        this.drugStockOutService = drugStockOutService;
    }



    /**
     * 列表查库存信息
     * @param storeId
     * @param drugName
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list (@RequestParam(value = "storeId") Integer storeId,
                          @RequestParam(value = "drugName", required = false) String drugName,
                          @RequestParam(value = "pageNo", required = false) Integer pageNo) {
        HashMap<String, Object> map = drugStockService.list(storeId, drugName, pageNo);
        return Response.buildSuccess(map);
    }


    /**
     * 点击库存条目,获得入库信息列表(查询库存数量和入库数量)
     * @param storeId
     * @param catalogId
     * @return
     */
    @RequestMapping(value = "catalogInList", method = RequestMethod.GET)
    public Response catalogInList (@RequestParam(value = "storeId") Integer storeId,
                          @RequestParam(value = "catalogId", required = false) Long catalogId) {
        HashMap<String, Object> map = drugStockService.catalogInList(storeId, catalogId, 1);
        return Response.buildSuccess(map);
    }


    /**
     * 点击库存条目,获得出库信息列表
     * @param storeId
     * @param catalogId
     * @return
     */
    @RequestMapping(value = "catalogOutList", method = RequestMethod.GET)
    public Response catalogOutList (@RequestParam(value = "storeId") Integer storeId,
                          @RequestParam(value = "catalogId", required = false) Long catalogId) {
        HashMap<String, Object> map = drugStockService.catalogOutList(storeId, catalogId, 1);
        return Response.buildSuccess(map);
    }




    /**
     * 暂时多余
     * 查看出库入库详情
     * @param drugId
     * @param batchNo
     * @param storeId
     * @return
     */
    @RequestMapping(value = "findInAndOutItem", method = RequestMethod.GET)
    public Response findInOrOutItem (@RequestParam(value = "drugId") Long drugId,
                                      @RequestParam(value = "batchNo", required = false) String batchNo,
                                      @RequestParam(value = "storeId") Integer storeId) {
        HashMap<String, Object> map = drugStockService.findInAndOutItem(drugId, batchNo, storeId);
        return Response.buildSuccess(map);
    }


    /**
     * 根据随货同行单查看相关进货信息
     * @return
     */
    @RequestMapping(value = "findByDeliverCode", method = RequestMethod.GET)
    public Response findByDeliverCode (@RequestParam(value = "deliverCode") String deliverCode,
                                       @RequestParam(value = "storeId") Integer storeId) {
        List<DrugDeliverItemDto> list = drugDeliveryItemService.findByDeliverCode(deliverCode, storeId);
        return Response.buildSuccess(list);
    }


    /**
     * 根据出库单号查询相关出库信息
     * @return
     */
    @RequestMapping(value = "findByOutNo", method = RequestMethod.GET)
    public Response findByOutNo (@RequestParam(value = "outNo") String outNo,
                                 @RequestParam(value = "storeId") Integer storeId) {
        List<DrugStockOutItemDto> list = drugStockOutService.findByStockOutNo(outNo, storeId);
        return Response.buildSuccess(list);
    }



}
