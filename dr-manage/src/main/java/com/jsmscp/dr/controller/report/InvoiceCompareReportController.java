package com.jsmscp.dr.controller.report;

import com.jsmscp.dr.service.impl.exception.ServiceException;
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
@RequestMapping("/report/invoiceCompare")
public class InvoiceCompareReportController {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceCompareReportController.class);

    /*发票核查*/

    private InvoiceCompareService invoiceCompareService;

    @Autowired
    public InvoiceCompareReportController(InvoiceCompareService invoiceCompareService) {
        this.invoiceCompareService = invoiceCompareService;
    }

    @GetMapping("invoiceCompare")
    public Response invoiceCompare(@RequestParam(value = "storeId") Integer storeId,
                                   @RequestParam(value = "invoiceCode", required = false) String invoiceCode,
                                   @RequestParam(value = "pageNo") Integer pageNo) {
        try{
            HashMap<String, Object> map = invoiceCompareService.findInvoiceReport(storeId, invoiceCode, pageNo);
            return Response.buildSuccess(map);
        } catch (ServiceException se) {
            return Response.fail(se.getMessage());
        }
    }

}
