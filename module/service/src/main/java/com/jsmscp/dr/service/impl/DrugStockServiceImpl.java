package com.jsmscp.dr.service.impl;

import com.jsmscp.dr.dto.DrugCatalogDto;
import com.jsmscp.dr.entity.DrugBase;
import com.jsmscp.dr.mapper.DrugBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.DrugStockInDto;
import com.jsmscp.dr.dto.DrugStockOutDetailDto;
import com.jsmscp.dr.dto.DrugDeliveryDto;
import com.jsmscp.dr.mapper.DrugCatalogMapper;
import com.jsmscp.dr.service.DrugStockService;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

@Service
public class DrugStockServiceImpl implements DrugStockService {

    private DrugCatalogMapper drugCatalogMapper;

    private DrugBaseMapper drugBaseMapper;

    @Autowired
    public DrugStockServiceImpl(DrugCatalogMapper drugCatalogMapper, DrugBaseMapper drugBaseMapper) {
        this.drugCatalogMapper = drugCatalogMapper;
        this.drugBaseMapper = drugBaseMapper;
    }



    /**
     * 列表查看库存
     * @param storeId
     * @param drugName
     * @param pageNo
     * @return
     */
    @Override
    public HashMap<String, Object> list(Integer storeId, String drugName, Integer pageNo) {
        DecimalFormat df = new DecimalFormat("0.000");
        HashMap<String, Object> map = new HashMap<>();
            map.put("pageNo", pageNo);
            map.put("pageSize", Constant.DEFAULT_PAGE_SIZE);
            Integer pageSize = Constant.DEFAULT_PAGE_SIZE;
            pageNo = (pageNo - 1) * pageSize;
            List<DrugCatalogDto> list = drugCatalogMapper.findDrugStock(storeId, drugName, pageNo, pageSize);
            //把药店药品单位转换成平台单位
            for (DrugCatalogDto lst : list) {
                if (lst.getConversionRatio() != null && lst.getConversionRatio() != 0 && lst.getDrugId() != null) {
                    DrugBase drugBase = drugBaseMapper.selectByPrimaryKey(lst.getDrugId());
                    BigDecimal stock;
                    if (null == lst.getStock()) {
                        lst.setStock(new BigDecimal(0));
                    } else {
                        stock = new BigDecimal(df.format(lst.getStock().divide(
                                new BigDecimal(lst.getConversionRatio()), 3)));
                        lst.setStock(stock);
                    }
                    lst.setUnit(drugBase.getUnit());
                }
            }
            map.put("list", list);
            Integer count = drugCatalogMapper.findStockCount(storeId, drugName);
            int total = count / pageSize;
            if (count % pageSize > 0) {
                total += 1;
            }
            map.put("count", count);
            map.put("total", total);
        return map;
    }


    /**
     * 列表查看入库库存(分页)
     * @param storeId
     * @param catalogId
     * @param pageNo
     * @return
     */
    @Override
    public HashMap<String, Object> catalogInList(Integer storeId, Long catalogId, Integer pageNo) {
        DecimalFormat df = new DecimalFormat("0.000");
        HashMap<String, Object> map = new HashMap<>();
        Integer pageSize = Constant.DRUG_PAGE_SIZE;
        if (storeId != null &&  catalogId != null) {
            map.put("pageNo", pageNo);
            pageNo = (pageNo - 1) * pageSize;
            List<DrugDeliveryDto> stockInList = drugCatalogMapper.findStockInItem(storeId, catalogId, pageNo, pageSize);
//            List<DrugStockMSGDto> drugStockOutMSG = drugCatalogMapper.findStockOutMSG(storeId, drugCode, batchNo);
//            List<DrugStockMSGDto> drugStockIntMSG = drugCatalogMapper.findStockInMSG(storeId, drugCode, batchNo);
            map.put("stockInList", stockInList);
            //查询全部库存数量
            Integer sumAll = drugCatalogMapper.findAllStockSum(storeId, catalogId);
            //查看入库库存数量
            Integer sumIn = drugCatalogMapper.findStockInSum(storeId, catalogId);
            //查看出库库存数量
            Integer sumOut  = drugCatalogMapper.findStockOutSum(storeId, catalogId);
            //查询入库数据
            int count = drugCatalogMapper.findDrugStockInCount(storeId, catalogId);
            int total = count / pageSize;
            if (count % pageSize > 0) {
                total += 1;
            }
            map.put("pageSize", pageSize);
            map.put("count", count);
            map.put("total", total);
            map.put("sumAll", sumAll);
            if (sumIn != null || sumOut != null) {
                map.put("sumIn", sumIn);
                map.put("sumOut", sumOut);
            }
//            map.put("drugStockOutMSG", drugStockOutMSG);
//            map.put("drugStockIntMSG", drugStockIntMSG);
        }
        return map;
    }
  /**
     * 列表查看出库库存(分页)
     * @param storeId
     * @param catalogId
     * @param pageNo
     * @return
     */
    @Override
    public HashMap<String, Object> catalogOutList(Integer storeId, Long catalogId, Integer pageNo) {
        DecimalFormat df = new DecimalFormat("0.000");
        HashMap<String, Object> map = new HashMap<>();
        Integer pageSize = Constant.DRUG_PAGE_SIZE;
        if (storeId != null &&  catalogId != null) {
            map.put("pageNo", pageNo);
            pageNo = (pageNo - 1) * pageSize;
            List<DrugStockOutDetailDto> stockOutList = drugCatalogMapper.findStockOutItem(storeId, catalogId,
                    pageNo, pageSize);
//            List<DrugStockMSGDto> drugStockOutMSG = drugCatalogMapper.findStockOutMSG(storeId, drugCode, batchNo);
//            List<DrugStockMSGDto> drugStockIntMSG = drugCatalogMapper.findStockInMSG(storeId, drugCode, batchNo);
            int count  = drugCatalogMapper.findStockOutCount(storeId, catalogId);
            int total = count / pageSize;
            if (count % pageSize > 0) {
                total += 1;
            }
            map.put("count", count);
            map.put("total", total);
            map.put("pageSize", pageSize);
            map.put("stockOutList", stockOutList);
//            map.put("drugStockOutMSG", drugStockOutMSG);
//            map.put("drugStockIntMSG", drugStockIntMSG);
        }
        return map;
    }


    /**
     * 查看出入库信息详情
     * @param drugId
     * @param batchNo
     * @param storeId
     * @return
     */
    @Override
    public HashMap<String, Object> findInAndOutItem(Long drugId, String batchNo, Integer storeId) {
        HashMap<String, Object> map = new HashMap<>();
        List<DrugStockInDto> deliverList = drugCatalogMapper.findDeliverItem(drugId, batchNo, storeId);
        List<DrugStockOutDetailDto> stockOutList = drugCatalogMapper.findInvoiceItem(drugId, batchNo, storeId);
        map.put("stockOutList", stockOutList);
        map.put("deliverList", deliverList);
        return map;
    }


}
