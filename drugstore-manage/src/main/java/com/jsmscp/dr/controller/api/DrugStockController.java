package com.jsmscp.dr.controller.api;


import com.jsmscp.dr.dto.DrugCatalogDto;
import com.jsmscp.dr.util.ExportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.service.DrugCatalogService;
import com.jsmscp.dr.util.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/DrugStock")
public class DrugStockController {
    private DrugCatalogService drugCatalogService;

    @Autowired
    public DrugStockController(DrugCatalogService drugCatalogService) {
        this.drugCatalogService = drugCatalogService;
    }

    /**
     * 查询库存
     *
     * @param storeId
     * @param keyword
     * @param keyword
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list(@RequestParam(value = "storeId") String storeId,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "pageNo") Integer pageNo) {
        HashMap<String, Object> map = drugCatalogService.findStock(storeId, keyword, pageNo);
        return Response.buildSuccess(map);
    }

    /**
     * 药店端库存管理数据导出
     * @param keyword
     */
    @RequestMapping(value = "export", method = RequestMethod.GET)
    public void export(@RequestParam(value = "keyword", required = false) String keyword,
                       HttpServletResponse response, HttpServletRequest request)
            throws IOException {

        String fileName = "库存管理"  + ".xls";
        String sheetName = "库存管理";
        List<DrugCatalogDto> list = drugCatalogService.exportStock(keyword);
        List<String> titles = new ArrayList<>();
        titles.add("通用名");
        titles.add("药品编码");
        titles.add("包装单位");
        titles.add("剂型");
        titles.add("规格");
        titles.add("厂家");
        titles.add("库存");
        titles.add("状态");
        titles.add("药店");
        ExportExcelUtil.exportStock(fileName, sheetName, titles, list, request, response);

    }

}
