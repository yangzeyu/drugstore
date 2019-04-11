package com.jsmscp.dr.controller.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/operation")
public class WebOperationController {

    @RequestMapping("drugStock")
    public String drugStock() {
        return "operation/drugStock/index";
    }

    @RequestMapping("drugStockOut")
    public String drugStockOut() {
        return "operation/drugStockOut/index";
    }

    @RequestMapping("drugStockIn")
    public String drugStockIn() {
        return "operation/drugStockIn/index";
    }

    @RequestMapping("drugCode")
    public String drugCode() {
        return "operation/drugCode/index";
    }

    @RequestMapping("uploadPrice")
    public String uploadPrice() {
        return "operation/uploadPrice/index";
    }
}
