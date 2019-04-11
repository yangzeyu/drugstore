package com.jsmscp.dr.controller.api;

import com.jsmscp.dr.dto.DrugCatalogDto;
import com.jsmscp.dr.service.DrugCatalogService;
import com.jsmscp.dr.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/drugCatalog")
public class DrugCatalogController {

    private DrugCatalogService drugCatalogService;

    @Autowired
    public DrugCatalogController(DrugCatalogService drugCatalogService) {
        this.drugCatalogService = drugCatalogService;
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


}
