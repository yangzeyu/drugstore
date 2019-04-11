package com.jsmscp.dr.controller.api;


import com.jsmscp.dr.entity.DrugBase;
import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.entity.Manufacture;
import com.jsmscp.dr.entity.MedicalInsurance;
import com.jsmscp.dr.entity.PlatformOperateLog;
import com.jsmscp.dr.service.DrugBaseService;
import com.jsmscp.dr.service.DrugCatalogService;
import com.jsmscp.dr.service.DrugStoreService;
import com.jsmscp.dr.service.ManufactureService;
import com.jsmscp.dr.service.PlatformOperateLogService;
import com.jsmscp.dr.service.MedicalInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.util.Response;

import java.util.List;


@RestController
@RequestMapping("/common")
public class CommonController {

    private DrugStoreService drugStoreService;
    private ManufactureService manufactureService;
    private DrugBaseService drugBaseService;
    private PlatformOperateLogService platformOperateLogService;
    private MedicalInsuranceService medicalInsuranceService;
    private DrugCatalogService drugCatalogService;

    @Autowired

    public CommonController(DrugStoreService drugStoreService, ManufactureService manufactureService,
                            DrugBaseService drugBaseService, PlatformOperateLogService platformOperateLogService,
                            MedicalInsuranceService medicalInsuranceService, DrugCatalogService drugCatalogService) {
        this.drugStoreService = drugStoreService;
        this.manufactureService = manufactureService;
        this.drugBaseService = drugBaseService;
        this.platformOperateLogService = platformOperateLogService;
        this.medicalInsuranceService = medicalInsuranceService;
        this.drugCatalogService = drugCatalogService;
    }

    /**
     * 查询所有药店.
     * @return
     */
    @RequestMapping(value = "findAllDrugStore", method = RequestMethod.GET)
    public Response findAllDrugStore(@RequestParam(value = "keyword", required = false) String keyword) {
        List<DrugStore> list = drugStoreService.findAllStore(keyword);
        return Response.buildSuccess(list);
    }


    /**
     * 查询所有生产厂商
     * @return
     */

    @RequestMapping(value = "findManufacture", method = RequestMethod.GET)
    public Response findManufacture(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Manufacture> list = manufactureService.findManufacture(keyword);
        return Response.buildSuccess(list);
    }

    /**
     * 查询平台所有药品
     * @return
     */
    @RequestMapping(value = "findAllDrug", method = RequestMethod.GET)
    public Response findAllDrug() {
        List<DrugBase> list = drugBaseService.findAllDrug();
        return Response.buildSuccess(list);
    }

    /**
     * 查询所有操作人
     * @return
     */
    @RequestMapping(value = "findAllOperator", method = RequestMethod.GET)
    public Response findAllOperator() {
        List<PlatformOperateLog> list = platformOperateLogService.findAllOperator();
        return Response.buildSuccess(list);
    }

    /**
     * 查询所有等级
     */
    @RequestMapping(value = "findAllGrade", method = RequestMethod.GET)
    public Response findAllGrade() {
        List<MedicalInsurance> list = medicalInsuranceService.findAllGrade();
        return Response.buildSuccess(list);
    }

    /**
     * 查询所有的医保目录名称
     * @return
     */
    @RequestMapping(value = "findAllMedicalInsuranceCode", method = RequestMethod.GET)
    public Response findAllMedicalInsuranceCode(@RequestParam(value = "keyword", required = false) String keyword) {
        List<MedicalInsurance> list  = medicalInsuranceService.findAllMedicalInsuranceCode(keyword);
        return Response.buildSuccess(list);
    }

    /**
     * 重新核算当前库存
     * @return
     */
    @RequestMapping(value = "compareStock", method = RequestMethod.GET)
    public void compareStock(@RequestParam(value = "storeId") Integer storeId) {
        drugCatalogService.compareStock(storeId);
    }
}
