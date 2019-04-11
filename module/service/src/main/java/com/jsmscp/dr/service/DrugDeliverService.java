package com.jsmscp.dr.service;

import com.jsmscp.dr.dto.DrugDeliverDto;
import com.jsmscp.dr.dto.DrugDeliverItemDto;

import java.util.HashMap;
import java.util.List;

public interface DrugDeliverService {
    HashMap<String, Object> list(String deliverCode, Integer storeId, String drugName, Byte type, String startTime,
                                String endTime, Integer pageNo);

    List<DrugDeliverItemDto> findDrugDeliverItem(Integer deliverId, Integer storeId);

    List<DrugDeliverItemDto> findDeliverDetail(Integer deliverId, Integer storeId);

    List<DrugDeliverDto> exportDrugDeliver(String deliverCode, Integer storeId, String drugName, Byte type,
                                           String startTime, String endTime);
}
