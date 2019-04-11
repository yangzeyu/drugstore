package com.jsmscp.dr.controller.api;


import com.jsmscp.dr.dto.DrugBaseDto;
import com.jsmscp.dr.entity.DrugCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.service.DrugBaseService;
import com.jsmscp.dr.service.DrugCatalogService;
import com.jsmscp.dr.util.Response;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("compareDrugCode")
public class CompareDrugController {

    private DrugCatalogService drugCatalogService;

    private DrugBaseService drugBaseService;

    @Autowired
    public CompareDrugController(DrugCatalogService drugCatalogService, DrugBaseService drugBaseService) {
        this.drugCatalogService = drugCatalogService;
        this.drugBaseService = drugBaseService;
    }


    /**
     * 查询所有药品目录.
     *
     * @return
     */
    @RequestMapping(value = "findByDrugId", method = RequestMethod.GET)
    public Response findByDrugId(@RequestParam(value = "storeId") Integer storeId,
                                 @RequestParam(value = "goodName", required = false) String goodName) {
        List<DrugCatalog> list = drugCatalogService.findByDrugId(storeId, goodName);
        return Response.buildSuccess(list);
    }


    /**
     * 查询所有药品目录.(下拉框)
     *
     * @return
     */
    @RequestMapping(value = "findAllCatalogByKey", method = RequestMethod.GET)
    public Response findAllCatalogByKey(@RequestParam(value = "keyword", required = false) String keyword,
                                        @RequestParam(value = "storeId") Integer storeId) {
        List<String> names = drugCatalogService.findAllCatalogByKey(keyword, storeId);
        return Response.buildSuccess(names);
    }


    /**
     * 查询平台药品信息
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list(@RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "manufactureId", required = false) Integer manufactureId) {
        List<DrugBaseDto> list = drugBaseService.findAllDrugBase(keyword, manufactureId);
        return Response.buildSuccess(list);
    }


    /**
     * 查询平台药品(下拉框)
     * @return
     */
    @RequestMapping(value = "findAllDrugByKey", method = RequestMethod.GET)
    public Response findAllDrug(@RequestParam(value = "keyword", required = false) String keyword) {
        List<String> names = drugBaseService.findAllDrugByKey(keyword);
        return Response.buildSuccess(names);
    }


    /**
     * 人工对码.
     *
     * @return
     */
    @RequestMapping(value = "compareDrug", method = RequestMethod.POST)
    public Response compareDrugCode(@RequestParam(value = "drugCode") String drugCode,
                                    @RequestParam(value = "drugId") Long drugId,
                                    @RequestParam(value = "conversionRatio") Integer conversionRatio,
                                    @RequestParam(value = "storeId") Integer storeId,
                                    @RequestParam(value = "catalogId") Long catalogId) {
        drugCatalogService.compareDrug(drugCode, drugId, storeId, conversionRatio, catalogId);
        return Response.buildSuccess("");
    }

    /**
     * 解除对码.
     *
     * @return
     */
    @RequestMapping(value = "delCompare", method = RequestMethod.POST)
    public Response delCompare(@RequestParam(value = "drugCode") String drugCode,
                               @RequestParam(value = "storeId") Integer storeId) {
        drugCatalogService.delCompare(drugCode, storeId);
        return Response.buildSuccess("");
    }


    /**
     * 查询对码药品信息.
     * @param catalogId
     * @return
     */
    @RequestMapping(value = "findCompared", method = RequestMethod.GET)
    public Response findCompared(@RequestParam(value = "catalogId") Long catalogId) {
        HashMap<String, Object> map = drugCatalogService.findCompared(catalogId);
        return Response.buildSuccess(map);
    }


}
