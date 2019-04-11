package com.jsmscp.dr.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.dto.CompareDrugCodeDto;
import com.jsmscp.dr.dto.DrugStockWarningDto;
import com.jsmscp.dr.service.DrugBatchNoService;
import com.jsmscp.dr.service.DrugStockWarningService;
import com.jsmscp.dr.util.Response;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    private DrugStockWarningService drugStockWarningService;

    private DrugBatchNoService drugBatchNoService;


    @Autowired
    public HomeController(DrugStockWarningService drugStockWarningService, DrugBatchNoService drugBatchNoService) {
        this.drugStockWarningService = drugStockWarningService;
        this.drugBatchNoService = drugBatchNoService;
    }

    /**
     * 列表查询报警信息
     * @return
     */
    @RequestMapping(value = "findAllWarning", method = RequestMethod.GET)
    public Response findAllWarning () {
        List<DrugStockWarningDto> list = drugStockWarningService.findAllWarning();
        return Response.buildSuccess(list);
    }

    /**
     * 列表查询对码信息
     * @return
     */
    @RequestMapping(value = "findAllNoDrugId", method = RequestMethod.GET)
    public Response findAllNoDrugId () {
        List<CompareDrugCodeDto> list = drugBatchNoService.findAllNoDrugId();
        return Response.buildSuccess(list);
    }






}
