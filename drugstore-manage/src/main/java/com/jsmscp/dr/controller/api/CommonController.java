package com.jsmscp.dr.controller.api;


import com.jsmscp.dr.entity.DrugBase;
import com.jsmscp.dr.entity.Manufacture;
import com.jsmscp.dr.service.DrugBaseService;
import com.jsmscp.dr.service.ManufactureService;
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


    private ManufactureService manufactureService;
    private DrugBaseService drugBaseService;

    @Autowired
    public CommonController(ManufactureService manufactureService, DrugBaseService drugBaseService) {
        this.manufactureService = manufactureService;
        this.drugBaseService = drugBaseService;
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

}
