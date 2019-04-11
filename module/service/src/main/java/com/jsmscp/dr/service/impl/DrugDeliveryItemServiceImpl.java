package com.jsmscp.dr.service.impl;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.DrugDeliverDto;
import com.jsmscp.dr.dto.UploadPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.dto.DrugDeliverItemDto;
import com.jsmscp.dr.mapper.DrugDeliveryMapper;
import com.jsmscp.dr.service.DrugDeliveryItemService;

import java.util.HashMap;
import java.util.List;

@Service
public class DrugDeliveryItemServiceImpl implements DrugDeliveryItemService {

    private DrugDeliveryMapper drugDeliveryMapper;

    @Autowired
    public DrugDeliveryItemServiceImpl(DrugDeliveryMapper drugDeliveryMapper) {
        this.drugDeliveryMapper = drugDeliveryMapper;
    }

    @Override
    public List<DrugDeliverItemDto> findByDeliverCode(String deliverCode, Integer storeId) {
        List<DrugDeliverItemDto> list = drugDeliveryMapper.findByDeliverCode(deliverCode, storeId);
        return list;
    }

    @Override
    public List<UploadPriceDto> findUploadPrice(String storeId, String startTime, String endTime) {
        List<UploadPriceDto> list = drugDeliveryMapper.findUploadPrice(storeId, startTime, endTime);
        return list;
    }
}
