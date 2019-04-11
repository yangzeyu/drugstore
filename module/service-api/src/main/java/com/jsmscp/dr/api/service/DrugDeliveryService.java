package com.jsmscp.dr.api.service;

import com.jsmscp.dr.api.service.exception.ServiceException;
import com.jsmscp.dr.api.vo.DrugItemVO;
import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.entity.DrugCatalog;
import com.jsmscp.dr.entity.DrugDelivery;
import com.jsmscp.dr.entity.DrugDeliveryItem;
import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.mapper.DrugCatalogMapper;
import com.jsmscp.dr.mapper.DrugDeliveryItemMapper;
import com.jsmscp.dr.mapper.DrugDeliveryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class DrugDeliveryService {

    private DrugDeliveryMapper drugDeliveryMapper;

    private DrugDeliveryItemMapper drugDeliveryItemMapper;

    private DrugCatalogMapper drugCatalogMapper;

    private ReentrantLock lock = new ReentrantLock();

    /**
     *
     * @param store
     * @param deliverCode
     * @param deliverDate
     * @param price
     * @param type
     * @param drugItems
     * @throws ServiceException
     */
    @Transactional
    public void saveDelivery(DrugStore store, String deliverCode, Date deliverDate, String price, Integer type,
                             List<DrugItemVO> drugItems, String idcard, String tradeNmae, String outNo
                            ) throws ServiceException {
        DrugDelivery delivery = drugDeliveryMapper.findByCode(deliverCode, store.getId().longValue());
        if (delivery != null) {
            throw new ServiceException("随货同行单号已存在");
        }
        lock.lock();
        delivery = new DrugDelivery();
        delivery.setCreateAt(new Date());
        delivery.setDeliverDate(deliverDate);
        delivery.setDeliverCode(deliverCode);
        delivery.setPrice(price);
        delivery.setIdcard(idcard);
        delivery.setTradeName(tradeNmae);
        delivery.setOutNo(outNo);
        delivery.setStoreId(store.getId().longValue());
        delivery.setType(type.byteValue());
        delivery.setUpdateAt(new Date());
        drugDeliveryMapper.insert(delivery);

        try {
            for (DrugItemVO vo : drugItems) {
                DrugDeliveryItem item = new DrugDeliveryItem();
                item.setDeliverId(delivery.getId());
                item.setDrugCode(vo.getDrugCode());
                DrugCatalog catalog = drugCatalogMapper.findByDrugCode(vo.getDrugCode(), store.getId());
                if (catalog != null) {
                    if (catalog.getDrugId() == null) {
                        catalog.setIsEmergent(Constant.IS_EMERGENT_TRUE);
                    } else {
                        item.setDrugId(catalog.getDrugId());
                    }
                    item.setCatalogId(catalog.getId());
                    if (null == catalog.getStock()) {
                        catalog.setStock(catalog.getInitStock().add(vo.getQuantity()
                                .setScale(3,BigDecimal.ROUND_DOWN)));
                    } else {
                        catalog.setStock(catalog.getStock().add(vo.getQuantity()
                                .setScale(3,BigDecimal.ROUND_DOWN)));
                    }
                    catalog.setStoreStock(vo.getPresentStock());
                    drugCatalogMapper.updateByPrimaryKey(catalog);
                } else {
                    throw new ServiceException("该" + vo.getDrugCode() + "药品未登记!");
                }
                item.setName(vo.getName());
                item.setQuantity(vo.getQuantity());
                item.setSpec(vo.getSpec());
                item.setDosageForm(vo.getDosageForm());
                item.setUnit(vo.getUnit());
                item.setUnitPrice(new BigDecimal(vo.getUnitPrice()));
                item.setBatchNo(vo.getBatchNo());
                item.setExpire(vo.getExpire());
                item.setMfr(vo.getMfr());
                item.setCreateAt(new Date());
                item.setUpdateAt(new Date());
                drugDeliveryItemMapper.insert(item);
                }
            } catch (NullPointerException ex) {
                throw new ServiceException("系统异常");
        } finally {
            lock.unlock();
        }
    }

    @Autowired
    public void setDrugDeliveryMapper(DrugDeliveryMapper drugDeliveryMapper) {
        this.drugDeliveryMapper = drugDeliveryMapper;
    }

    @Autowired
    public void setDrugDeliveryItemMapper(DrugDeliveryItemMapper drugDeliveryItemMapper) {
        this.drugDeliveryItemMapper = drugDeliveryItemMapper;
    }

    @Autowired
    public void setDrugCatalogMapper(DrugCatalogMapper drugCatalogMapper) {
        this.drugCatalogMapper = drugCatalogMapper;
    }
}
