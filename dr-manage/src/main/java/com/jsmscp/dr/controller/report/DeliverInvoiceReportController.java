package com.jsmscp.dr.controller.report;

import com.jsmscp.dr.service.report.InvoiceCompareService;
import com.jsmscp.dr.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/report/invoiceDeliver")
public class DeliverInvoiceReportController {
    private static final Logger LOG = LoggerFactory.getLogger(DeliverInvoiceReportController.class);

    /*
    *药店进货与发票核算
    *
    * */

    private InvoiceCompareService invoiceCompareService;

    @Autowired
    public DeliverInvoiceReportController(InvoiceCompareService invoiceCompareService) {
        this.invoiceCompareService = invoiceCompareService;
    }

    @GetMapping("invoiceDeliver")
    public Response invoiceCompare(@RequestParam(value = "storeId") Integer storeId,
                                   @RequestParam(value = "invoiceCode", required = false) String invoiceCode,
                                   @RequestParam(value = "invoiceType", required = false) Byte invoiceType,
                                   @RequestParam(value = "pageNo") Integer pageNo) {
        HashMap<String, Object> map = invoiceCompareService.findInvoiceDeliverReport(storeId, invoiceCode,
                invoiceType, pageNo);
        return Response.buildSuccess(map);
    }



}
