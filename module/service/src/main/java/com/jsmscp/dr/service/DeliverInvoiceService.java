package com.jsmscp.dr.service;



import java.util.HashMap;

public interface DeliverInvoiceService {
    HashMap<String, Object> list(Integer storeId, String keyword, String startTime, String endTime, Integer pageNo);

    HashMap<String, Object> findInvoiceImg(Integer deliveryId, String imagePrefix);
}
