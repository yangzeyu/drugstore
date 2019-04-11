package com.jsmscp.dr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.DrugDeliverDto;
import com.jsmscp.dr.dto.DrugDeliverItemDto;
import com.jsmscp.dr.entity.DrugCatalog;
import com.jsmscp.dr.mapper.DrugCatalogMapper;
import com.jsmscp.dr.mapper.DrugDeliveryMapper;
import com.jsmscp.dr.service.DrugDeliverService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DrugDeliverServiceImpl implements DrugDeliverService {

    private DrugDeliveryMapper drugDeliveryMapper;

    private DrugCatalogMapper drugCatalogMapper;

    @Autowired
    public DrugDeliverServiceImpl(DrugDeliveryMapper drugDeliveryMapper, DrugCatalogMapper drugCatalogMapper) {
        this.drugDeliveryMapper = drugDeliveryMapper;
        this.drugCatalogMapper = drugCatalogMapper;
    }



    /**
     * 查询药品随货同行单
     * @param deliverCode
     * @param storeId
     * @param drugName
     * @param type
     * @param startTime
     * @param endTime
     * @param pageNo
     * @return
     */
    @Override
    public HashMap<String, Object> list(String deliverCode, Integer storeId, String drugName, Byte type,
                                        String startTime, String endTime, Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNo);
        map.put("pageSize", Constant.DEFAULT_PAGE_SIZE);
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        List<DrugDeliverDto> list = drugDeliveryMapper.findDrugDeliver(deliverCode, storeId, drugName, type, startTime,
                endTime, pageNo, pageSize);
        List<Integer> deliverIds = new ArrayList<>();
        //为空时判断？
        if (list != null && list.size() > 0) {
            for (DrugDeliverDto ddd : list) {
                deliverIds.add(ddd.getId());
            }
            if (deliverIds.size() > 0) {
                List<DrugDeliverDto> isPairCode = drugDeliveryMapper.findIsPairCode(deliverIds);
                for (DrugDeliverDto ddd: list) {
                    for (DrugDeliverDto pairCodeStatus : isPairCode) {
                        if (ddd.getId().equals(pairCodeStatus.getId())) {
                            if (Constant.IS_PAIR_CODE_FALSE.equals(pairCodeStatus.getIsPairCode())) {
                                ddd.setIsPairCode(pairCodeStatus.getIsPairCode());
                                break;
                            } else {
                                ddd.setIsPairCode(Constant.IS_PAIR_CODE_TRUE);
                            }
                        }
                    }
                }
            }
        }

        Integer count = drugDeliveryMapper.findCount(deliverCode, storeId, drugName, type, startTime, endTime);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        map.put("list", list);
        return map;
    }


    /**
     * 查询随货同行单明细
     * @param deliverId
     * @return
     */

    @Override
    public List<DrugDeliverItemDto> findDrugDeliverItem(Integer deliverId, Integer storeId) {
        List<DrugDeliverItemDto> list = drugDeliveryMapper.findDrugDeliverItem(deliverId);
        for (DrugDeliverItemDto d : list) {
            if (d.getDrugId() != null) {
                DrugCatalog drugCatalog = drugCatalogMapper.findByDrugCode(d.getDrugCode(), storeId);
                if (drugCatalog != null) {
                    d.setQuantity(d.getQuantity().divide(new BigDecimal(drugCatalog.getConversionRatio()), 3 ));
                    d.setUnit(drugCatalog.getUnit());
                }
            }
            if (Constant.IS_PAIR_CODE_FALSE.equals(d.getIsPairCode())) {
                d.setIsPairCode(Constant.IS_PAIR_CODE_FALSE);
            } else {
                d.setIsPairCode(Constant.IS_PAIR_CODE_TRUE);
            }
        }
        return list;
    }


    /**
     *
     * @param deliverId
     * @param storeId
     * @return
     */
    @Override
    public List<DrugDeliverItemDto> findDeliverDetail(Integer deliverId, Integer storeId) {
        List<DrugDeliverItemDto> list = drugDeliveryMapper.findDeliverDetail(deliverId);
        for (DrugDeliverItemDto d : list) {
            if (d.getDrugId() != null) {
                DrugCatalog drugCatalog = drugCatalogMapper.findByDrugCode(d.getDrugCode(), storeId);
                if (drugCatalog != null) {
                    d.setQuantity(d.getQuantity().divide(new BigDecimal(drugCatalog.getConversionRatio()),
                            3 ));
                    d.setUnit(drugCatalog.getUnit());
                }
            }
        }
        return list;
    }

    @Override
    public List<DrugDeliverDto> exportDrugDeliver(String deliverCode, Integer storeId, String drugName, Byte type,
                                                  String startTime, String endTime) {
        List<DrugDeliverDto> list = drugDeliveryMapper.exportDrugDeliver(deliverCode, storeId, drugName, type, startTime,
                endTime);

        for (DrugDeliverDto drugDeliverDto:list) {
            if (drugDeliverDto.getInvoiceId() != null) {
                drugDeliverDto.setInvoiceStatus(Constant.SUCCESS_INVOICE_STATUS);
            } else {
                drugDeliverDto.setInvoiceStatus(Constant.ERROR_INVOICE_STATUS);
            }
        }
        return list;
    }
}
