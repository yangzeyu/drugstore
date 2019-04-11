package com.jsmscp.dr.controller.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/operation")
public class WebOperationController {

    @RequestMapping("drugBase")
    public String drugBase() {
        return "operation/drugBase/index";
    }

    @RequestMapping("drugList")
    public String drugList() {
        return "operation/drugList/index";
    }

    @RequestMapping("sendLog")
    public String sendLog() {
        return "operation/sendLog/index";
    }

    @RequestMapping("invoice")
    public String invoice() {
        return "operation/invoice/index";
    }

    @RequestMapping("drugStore")
    public String drugStore() {
        return "operation/drugStore/index";
    }

    @RequestMapping("drugStockOut")
    public String drugStockOut() {
        return "operation/drugStockOut/index";
    }

    @RequestMapping("drugStockIn")
    public String drugStockIn() {
        return "operation/drugStockIn/index";
    }

    @RequestMapping("operateLog")
    public String operateLog() {
        return "operation/operateLog/index";
    }

    @RequestMapping("drugStock")
    public String drugStock() {
        return "operation/drugStock/index";
    }

    @RequestMapping("drugCode")
    public String drugCode() {
        return "operation/drugCode/index";
    }

    @RequestMapping("medicalInsurance")
    public String medicalInsurance() {
        return "operation/medicalInsurance/index";
    }

    @RequestMapping("medicalInsuranceCode")
    public String medicalInsuranceCode() {
        return "operation/medicalInsuranceCode/index";
    }

    @RequestMapping("storeOutIn")
    public String storeOutIn() {
        return "operation/storeOutIn/index";
    }

    @RequestMapping("drugBaseReport")
    public String drugBaseReport() {
        return "operation/drugBaseReport/index";
    }

    @RequestMapping("insuranceReport")
    public String insuranceReport() {
        return "operation/insuranceReport/index";
    }

    @RequestMapping("invoiceDeliver")
    public String invoiceDeliver() {
        return "operation/invoiceDeliver/index";
    }

    @RequestMapping("invoiceCompare")
    public String invoiceCompare() {
        return "operation/invoiceCompare/index";
    }

}
