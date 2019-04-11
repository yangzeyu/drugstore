package com.jsmscp.dr.dto;

import java.util.List;

public class QueryInvoiceDto {

    private List<InvoiceDrugDto> goodsData;

    private String success;

    private String date;

    private String errCode;

    public List<InvoiceDrugDto> getGoodsData() {
        return goodsData;
    }

    public void setGoodsData(List<InvoiceDrugDto> goodsData) {
        this.goodsData = goodsData;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
