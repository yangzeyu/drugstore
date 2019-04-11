package com.jsmscp.dr.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.service.DeliverInvoiceService;
import com.jsmscp.dr.util.Response;

import java.util.HashMap;


@RestController
@RequestMapping("/DeliverInvoice")
public class DeliverInvoiceLogController {

    private DeliverInvoiceService deliverInvoiceService;

    @Autowired
    public DeliverInvoiceLogController(DeliverInvoiceService deliverInvoiceService) {
        this.deliverInvoiceService = deliverInvoiceService;
    }

    @Value("${path.imagePrefix}")
    private String imagePrefix;
    /**
     * 查询发票和随货同行单.
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list(@RequestParam(value = "storeId", required = false) Integer storeId,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endTime", required = false) String endTime,
                         @RequestParam(value = "pageNo") Integer pageNo) {
        HashMap<String, Object> map = deliverInvoiceService.list(storeId, keyword, startTime, endTime, pageNo);
        return Response.buildSuccess(map);
    }

    /**
     * 查询发票图片.
     * @return
     */
    @RequestMapping(value = "findInvoiceImg", method = RequestMethod.GET)
    public Response findInvoiceImg(@RequestParam(value = "deliveryId", required = false) Integer deliveryId) {
        HashMap<String, Object> map = deliverInvoiceService.findInvoiceImg(deliveryId,imagePrefix);
        return Response.buildSuccess(map);
    }

}
