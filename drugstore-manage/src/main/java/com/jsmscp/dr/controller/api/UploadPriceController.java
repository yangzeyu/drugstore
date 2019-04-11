package com.jsmscp.dr.controller.api;

import com.jsmscp.dr.dto.UploadPriceDto;
import com.jsmscp.dr.service.DrugDeliveryItemService;
import com.jsmscp.dr.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/uploadPrice")
public class UploadPriceController {

    private DrugDeliveryItemService drugDeliveryItemService;

    @Autowired
    public UploadPriceController(DrugDeliveryItemService drugDeliveryItemService) {
        this.drugDeliveryItemService = drugDeliveryItemService;
    }

    /**
     * 查询上传数据金额
     *
     * @param storeId
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "findUploadPrice", method = RequestMethod.GET)
    public Response list(@RequestParam(value = "storeId") String storeId,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endTime", required = false) String endTime) {
        List<UploadPriceDto> list = drugDeliveryItemService.findUploadPrice(storeId, startTime, endTime);
        return Response.buildSuccess(list);
    }



}
