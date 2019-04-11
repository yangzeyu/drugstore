package com.jsmscp.dr.service;

import com.jsmscp.dr.dto.DrugStockOutDto;
import com.jsmscp.dr.dto.DrugStockOutItemDto;

import java.util.HashMap;
import java.util.List;

public interface DrugStockOutService {
    HashMap<String, Object> findDrugStockOut(String outNo, Integer drugStoreId, Byte type, String drugName,
                                             Integer payType, String startTime, String endTime, Integer pageNo);

    List<DrugStockOutItemDto> findDrugStockOutItem(String storeOutId, Integer storeId);

    List<DrugStockOutItemDto> findByStockOutNo(String outNo, Integer storeId);

    List<DrugStockOutItemDto> findStoreItem(String storeOutId, Integer storeId);

    List<DrugStockOutDto> exportStockOut(String outNo, Integer drugStoreId, Byte type, Integer payType,
                                         String startTime, String endTime);
}
