package com.jsmscp.dr.service.report;


import java.util.HashMap;

public interface InvoiceCompareService {

    HashMap<String, Object> findInvoiceReport(Integer storeId, String invoiceCode, Integer pageNo);

    HashMap<String, Object> findInvoiceDeliverReport(Integer storeId, String invoiceCode,
                                                    Byte invoiceType, Integer pageNo);
}
