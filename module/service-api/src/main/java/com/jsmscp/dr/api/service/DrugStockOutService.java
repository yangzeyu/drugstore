package com.jsmscp.dr.api.service;

import com.jsmscp.dr.api.service.exception.ServiceException;
import com.jsmscp.dr.api.vo.DrugItemVO;
import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.entity.DrugBatchItem;
import com.jsmscp.dr.entity.DrugCatalog;
import com.jsmscp.dr.entity.DrugStockOut;
import com.jsmscp.dr.entity.DrugStockOutItem;
import com.jsmscp.dr.entity.DrugStockWarning;
import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.mapper.DrugBatchItemMapper;
import com.jsmscp.dr.mapper.DrugCatalogMapper;
import com.jsmscp.dr.mapper.DrugStockOutItemMapper;
import com.jsmscp.dr.mapper.DrugStockOutMapper;
import com.jsmscp.dr.mapper.DrugStockWarningMapper;

import com.jsmscp.dr.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class DrugStockOutService {

    private DrugStockOutMapper drugStockOutMapper;

    private DrugStockOutItemMapper drugStockOutItemMapper;

    private DrugCatalogMapper drugCatalogMapper;

    private DrugStockWarningMapper drugStockWarningMapper;

    private DrugBatchItemMapper drugBatchItemMapper;

    private ReentrantLock lock = new ReentrantLock();

    /**
     * 根据出库单更新库存
     *
     * @param store
     * @param outNo
     * @param price
     * @param type
     * @param outDate
     * @param drugItems
     * @throws ServiceException
     */
    @Transactional
    public void saveStockOut(DrugStore store, String outNo, String price, Integer payType, Integer type,
                             Date outDate, List<DrugItemVO> drugItems, String insurancePrice,
                             String tradeName, String idcard) throws ServiceException {

        DrugStockOut out = drugStockOutMapper.findByOutNo(outNo, store.getId());
        if (out != null) {
            throw new ServiceException("出库单号已存在");
        }

        out = new DrugStockOut();
        out.setCreateAt(new Date());
        out.setOutDate(outDate);
        out.setStoreId(store.getId());
        out.setOutNo(outNo);
        out.setType(type.byteValue());
        out.setPayType(payType);
        out.setPrice(price);
        out.setTradeName(tradeName);
        if (!StringUtils.isBlank(insurancePrice)) {
            out.setInsurancePrice(new BigDecimal(insurancePrice));
        }
        out.setIdcard(idcard);
        out.setUpdateAt(new Date());

        drugStockOutMapper.insert(out);
        lock.lock();
        try{
        for (DrugItemVO vo : drugItems) {
            DrugStockOutItem item = new DrugStockOutItem();
            item.setStoreOutId(out.getId());
            item.setDrugCode(vo.getDrugCode());
            DrugCatalog catalog = drugCatalogMapper.findByDrugCode(vo.getDrugCode(), store.getId());
            DrugStockWarning drugStockWarning = new DrugStockWarning();
            if (catalog != null) {
                drugStockWarning.setPlatformCode(vo.getDrugCode());
                if (catalog.getDrugId() != null) {
                    item.setDrugId(catalog.getDrugId());
                } else {
                    catalog.setIsEmergent(Constant.IS_EMERGENT_TRUE);
                }

                item.setCatalogId(catalog.getId());
                if (null == catalog.getStock()) {
                    catalog.setStock(catalog.getInitStock().subtract(vo.getQuantity()
                            .setScale(3,BigDecimal.ROUND_DOWN)));
                } else {
                    catalog.setStock(catalog.getStock().subtract(vo.getQuantity()
                            .setScale(3,BigDecimal.ROUND_DOWN)));
                }
                    catalog.setStoreStock(vo.getPresentStock());
                drugCatalogMapper.updateByPrimaryKey(catalog);

                // 判断库存是否有异常
                DrugBatchItem drugBatchItem = drugBatchItemMapper.findByStoreId(store.getId(), Constant.LEAST_STOCK);
                //判断实时库存与系统之间的库存是否一致
                if (!catalog.getStock().equals(catalog.getStoreStock())) {
                    drugStockWarning.setDrugId(catalog.getDrugId());
                    drugStockWarning.setStatus(Constant.STOCK_WARNING_NOT_READ);
                    drugStockWarning.setStoreId(store.getId());
                    drugStockWarning.setCatalogId(catalog.getId());
                    drugStockWarning.setType(Constant.STORE_STOCK_WARNING);
                    drugStockWarning.setCreateAt(new Date());
                    drugStockWarning.setUpdateAt(new Date());
                    drugStockWarningMapper.insert(drugStockWarning);
                }
                int flag = catalog.getStock().compareTo(new BigDecimal(0));
                if (flag < 0) {  //单个药品库存异常
                    drugStockWarning.setDrugId(catalog.getDrugId());
                    drugStockWarning.setStatus(Constant.STOCK_WARNING_NOT_READ);
                    drugStockWarning.setStoreId(store.getId());
                    drugStockWarning.setCatalogId(catalog.getId());
                    drugStockWarning.setType(Constant.STOCK_WARNING);
                    drugStockWarning.setCreateAt(new Date());
                    drugStockWarning.setUpdateAt(new Date());
                    drugStockWarningMapper.insert(drugStockWarning);
                } else if (drugBatchItem != null) {  //药品批次库存异常
                    drugStockWarning.setDrugId(drugBatchItem.getDrugId());
                    drugStockWarning.setType(Constant.STOCK_BATCH_NO_WARNING);
                    drugStockWarning.setStoreId(store.getId());
                    drugStockWarning.setCatalogId(catalog.getId());
                    drugStockWarning.setStatus(Constant.STOCK_WARNING_NOT_READ);
                    drugStockWarning.setBatchNo(drugBatchItem.getBatchNo());
                    drugStockWarning.setCreateAt(new Date());
                    drugStockWarning.setUpdateAt(new Date());
                    drugStockWarningMapper.insert(drugStockWarning);
                }
            } else {
                throw new ServiceException("该" + vo.getDrugCode() + "药品未登记");
            }
//            item.setName(vo.getName());
            item.setQuantity(vo.getQuantity());
            item.setSpec(vo.getSpec());
            item.setDosageForm(vo.getDosageForm());
            item.setUnit(vo.getUnit());
            item.setBatchNo(vo.getBatchNo());
            item.setExpire(vo.getExpire());
            item.setInsuranceFlag(vo.getInsuranceFlag());
            try {
                item.setUnitPrice(new BigDecimal(vo.getUnitPrice()));
            } catch (Exception ex) {
                throw new ServiceException("出库药品单价格式错误!");
            }
            item.setMfr(vo.getMfr());
            item.setCreateAt(new Date());
            item.setUpdateAt(new Date());
            drugStockOutItemMapper.insert(item);
            }
        } catch (NullPointerException ex){
            throw new ServiceException("系统异常");
        } finally {
            lock.unlock();
        }
    }

    @Autowired
    public void setDrugStockOutMapper(DrugStockOutMapper drugStockOutMapper) {
        this.drugStockOutMapper = drugStockOutMapper;
    }

    @Autowired
    public void setDrugStockOutItemMapper(DrugStockOutItemMapper drugStockOutItemMapper) {
        this.drugStockOutItemMapper = drugStockOutItemMapper;
    }

    @Autowired
    public void setDrugCatalogMapper(DrugCatalogMapper drugCatalogMapper) {
        this.drugCatalogMapper = drugCatalogMapper;
    }

    @Autowired
    public void DrugStockWarningMapper(DrugStockWarningMapper drugStockWarningMapper) {
        this.drugStockWarningMapper = drugStockWarningMapper;
    }

    @Autowired
    public void DrugBatchItemMapper(DrugBatchItemMapper drugBatchItemMapper) {
        this.drugBatchItemMapper = drugBatchItemMapper;
    }
}
