package com.jsmscp.dr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.DrugStockOutDto;
import com.jsmscp.dr.dto.DrugStockOutItemDto;
import com.jsmscp.dr.entity.DrugCatalog;
import com.jsmscp.dr.mapper.DrugCatalogMapper;
import com.jsmscp.dr.mapper.DrugStockOutMapper;
import com.jsmscp.dr.service.DrugStockOutService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DrugStockOutServiceImpl implements DrugStockOutService {

    private DrugStockOutMapper drugStockOutMapper;

    private DrugCatalogMapper drugCatalogMapper;

    @Autowired
    public DrugStockOutServiceImpl(DrugStockOutMapper drugStockOutMapper, DrugCatalogMapper drugCatalogMapper) {
        this.drugStockOutMapper = drugStockOutMapper;
        this.drugCatalogMapper = drugCatalogMapper;
    }




    /**
     *
     * 查询药品出库信息
     * @param outNo
     * @param drugStoreId
     * @param type
     * @param drugName
     * @param startTime
     * @param endTime
     * @param pageNo
     * @return
     */
    @Override
    public HashMap<String, Object> findDrugStockOut(String outNo, Integer drugStoreId, Byte type, String drugName,
                                                    Integer payType, String startTime, String endTime, Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNo);
        map.put("pageSize", Constant.DEFAULT_PAGE_SIZE);
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        List<DrugStockOutDto> list = drugStockOutMapper.findDrugStockOut(outNo, drugStoreId, type, drugName, payType,
                startTime, endTime, pageNo, pageSize);
        List<Integer> stockOutIds = new ArrayList<>();
        for (DrugStockOutDto dsod: list) {
            stockOutIds.add(dsod.getId());
        }
        if (stockOutIds.size() != 0) {
            List<DrugStockOutDto> isPairCode = drugStockOutMapper.findIsPairCode(stockOutIds);
            for (DrugStockOutDto pairCodeStatus : isPairCode) {
                for (DrugStockOutDto dsod: list) {
                    if (dsod.getId().equals(pairCodeStatus.getId())) {
                        if (Constant.IS_PAIR_CODE_FALSE.equals(pairCodeStatus.getIsPairCode())) {
                            dsod.setIsPairCode(Constant.IS_PAIR_CODE_FALSE);
                            break;
                        } else {
                            dsod.setIsPairCode(Constant.IS_PAIR_CODE_TRUE);
                        }
                    }
                }
            }
        }
        Integer count = drugStockOutMapper.findCount(outNo, drugStoreId, type, drugName, payType, startTime, endTime);
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
     * 根据出库单查询出库详情
     * @param storeOutId
     * @return
     */
    @Override
    public List<DrugStockOutItemDto> findDrugStockOutItem(String storeOutId, Integer storeId) {
        List<DrugStockOutItemDto> list = drugStockOutMapper.findDrugStockOutItem(storeOutId);
        for (DrugStockOutItemDto dsoi: list) {
            if (dsoi.getDrugId() != null) {
                DrugCatalog drugCatalog = drugCatalogMapper.findByDrugCode(dsoi.getDrugCode(), storeId);
                if (drugCatalog != null) {
                    dsoi.setQuantity(dsoi.getQuantity().divide(new BigDecimal(drugCatalog.getConversionRatio()), 3));
                    dsoi.setUnit(drugCatalog.getUnit());
                }

            }
            if (Constant.IS_PAIR_CODE_FALSE.equals(dsoi.getIsPairCode())) {
                dsoi.setIsPairCode(Constant.IS_PAIR_CODE_FALSE);
            } else {
                dsoi.setIsPairCode(Constant.IS_PAIR_CODE_TRUE);
            }

        }
        return list;
    }

    /**
     * 根据出库编码查询出库详情
     * @param outNo
     * @param storeId
     * @return
     */
    @Override
    public List<DrugStockOutItemDto> findByStockOutNo(String outNo, Integer storeId) {
        List<DrugStockOutItemDto> list = drugStockOutMapper.findByStockOutNo(outNo, storeId);
        for (DrugStockOutItemDto dsoi: list) {
            if (dsoi.getDrugId() != null) {
                DrugCatalog drugCatalog = drugCatalogMapper.findByDrugCode(dsoi.getDrugCode(), storeId);
                if (drugCatalog != null) {
                    dsoi.setQuantity(dsoi.getQuantity().divide(new BigDecimal(drugCatalog.getConversionRatio()), 3));
                    dsoi.setUnit(drugCatalog.getUnit());
                }
            }
        }
        return list;
    }

    @Override
    public List<DrugStockOutItemDto> findStoreItem(String storeOutId, Integer storeId) {
        List<DrugStockOutItemDto> list = drugStockOutMapper.findDrugStockOutItem(storeOutId);
        return list;
    }

    /**
     * 出库导出
     * @param outNo
     * @param drugStoreId
     * @param type
     * @param payType
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<DrugStockOutDto> exportStockOut(String outNo, Integer drugStoreId, Byte type, Integer payType,
                                                String startTime, String endTime) {
        List<DrugStockOutDto> list = drugStockOutMapper.exportStockOut(outNo, drugStoreId, type, payType,
                startTime, endTime);
        return list;
    }
}
