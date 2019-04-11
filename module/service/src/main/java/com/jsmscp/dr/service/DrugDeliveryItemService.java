package com.jsmscp.dr.service;

import com.jsmscp.dr.dto.DrugDeliverItemDto;
import com.jsmscp.dr.dto.UploadPriceDto;

import java.util.HashMap;
import java.util.List;

public interface DrugDeliveryItemService {


    List<DrugDeliverItemDto> findByDeliverCode(String deliverCode, Integer storeId);

    List<UploadPriceDto> findUploadPrice(String storeId, String startTime, String endTime);
}
