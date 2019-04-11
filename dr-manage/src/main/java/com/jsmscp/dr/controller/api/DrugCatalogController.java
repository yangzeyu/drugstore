package com.jsmscp.dr.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.dto.DrugCatalogDto;
import com.jsmscp.dr.service.DrugCatalogService;
import com.jsmscp.dr.util.Response;

import java.util.HashMap;

@RestController
@RequestMapping("/drugCatalog")
public class DrugCatalogController {

    private DrugCatalogService drugCatalogService;

    @Autowired
    public DrugCatalogController(DrugCatalogService drugCatalogService) {
        this.drugCatalogService = drugCatalogService;
    }

    /**
     * 查询药品目录列表
     *
     * @param keyword
     * @param status
     * @param storeId
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list(@RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "storeId", required = false) Integer storeId,
                         @RequestParam(value = "status", required = false) Byte status,
                         @RequestParam(value = "pageNo") Integer pageNo) {
        HashMap<String, Object> map = drugCatalogService.list(keyword, storeId, status, pageNo);
        return Response.buildSuccess(map);
    }


    /**
     * 查询单个药品目录
     *
     * @param drugCatalogId
     * @return
     */
    @RequestMapping(value = "findOne", method = RequestMethod.GET)
    public Response findOne(@RequestParam(value = "drugCatalogId", required = false) Long drugCatalogId) {
        DrugCatalogDto drugCatalogDto = drugCatalogService.findOne(drugCatalogId);
        return Response.buildSuccess(drugCatalogDto);
    }

    /**
     * 新增药品目录(暂时不用)
     *
     * @param drugId
     * @param storeId
     * @param drugCode
     * @param unit
     * @param goodName
     * @param maxOnceNumber
     * @param lowerLimit
     * @param upperLimit
     * @param onceUnit
     * @param onceNumber
     * @param freqName
     * @param freqCode
     * @param direction
     * @param status
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Response add(@RequestParam(value = "drugId") Long drugId,
                        @RequestParam(value = "storeId") Integer storeId,
                        @RequestParam(value = "drugCode") String drugCode,
                        @RequestParam(value = "unit") String unit,
                        @RequestParam(value = "goodName") String goodName,
                        @RequestParam(value = "maxOnceNumber") Integer maxOnceNumber,
                        @RequestParam(value = "lowerLimit") Integer lowerLimit,
                        @RequestParam(value = "upperLimit") Integer upperLimit,
                        @RequestParam(value = "onceUnit", required = false) String onceUnit,
                        @RequestParam(value = "onceNumber", required = false) String onceNumber,
                        @RequestParam(value = "freqName", required = false) String freqName,
                        @RequestParam(value = "freqCode", required = false) String freqCode,
                        @RequestParam(value = "direction", required = false) String direction,
                        @RequestParam(value = "status") Byte status) {
        Long id = drugCatalogService.add(drugId, storeId, drugCode, unit, goodName, maxOnceNumber, lowerLimit,
                upperLimit, status, onceUnit, onceNumber, freqName, freqCode, direction);
        return Response.buildSuccess(id);
    }

    /**
     * 修改药品目录
     *
     * @param drugCatalogId
     * @param maxOnceNumber
     * @param lowerLimit
     * @param upperLimit
     * @param status
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Response edit(@RequestParam(value = "drugCatalogId") Long drugCatalogId,
                         @RequestParam(value = "maxOnceNumber") Integer maxOnceNumber,
                         @RequestParam(value = "lowerLimit") Integer lowerLimit,
                         @RequestParam(value = "upperLimit") Integer upperLimit,
                         @RequestParam(value = "status") Byte status) {
        drugCatalogService.edit(drugCatalogId, maxOnceNumber, lowerLimit, upperLimit, status);
        return Response.buildSuccess("success");
    }
}
