package com.jsmscp.dr.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.dto.DrugDeliverItemDto;
import com.jsmscp.dr.service.DrugDeliverService;
import com.jsmscp.dr.util.Response;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/drugDeliver")
public class DeliveryController {

    private DrugDeliverService drugDeliverService;

    @Autowired
    public DeliveryController(DrugDeliverService drugDeliverService) {
        this.drugDeliverService = drugDeliverService;
    }

    /**
     * 列表查询进货单
     *
     * @param deliverCode
     * @param storeId
     * @param startTime
     * @param endTime
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list(@RequestParam(value = "deliverCode", required = false) String deliverCode,
                         @RequestParam(value = "storeId") Integer storeId,
                         @RequestParam(value = "drugName", required = false) String drugName,
                         @RequestParam(value = "type", required = false) Byte type,
                         @RequestParam(value = "startTIme", required = false) String startTime,
                         @RequestParam(value = "endTime", required = false) String endTime,
                         @RequestParam(value = "pageNo") Integer pageNo) {
        HashMap<String, Object> map = drugDeliverService.list(deliverCode, storeId, drugName, type, startTime, endTime,
                pageNo);
        return Response.buildSuccess(map);
    }


    /**
     * 列表查询进货单详情
     *
     * @param deliverId
     * @return
     */
    @RequestMapping(value = "findDrugDeliverItem", method = RequestMethod.GET)
    public Response findDrugDeliverItem(@RequestParam(value = "deliverId") Integer deliverId,
                                        @RequestParam(value = "storeId") Integer storeId) {
        List<DrugDeliverItemDto> list = drugDeliverService.findDrugDeliverItem(deliverId, storeId);
        return Response.buildSuccess(list);
    }
}
