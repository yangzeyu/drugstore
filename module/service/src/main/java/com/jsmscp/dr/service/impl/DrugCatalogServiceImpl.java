package com.jsmscp.dr.service.impl;

import com.jsmscp.dr.dto.DrugDeliverDto;
import com.jsmscp.dr.dto.DrugDeliveryDto;
import com.jsmscp.dr.dto.DrugStockOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.DrugBaseDto;
import com.jsmscp.dr.dto.DrugCatalogDto;
import com.jsmscp.dr.entity.DrugCatalog;
import com.jsmscp.dr.entity.DrugDelivery;
import com.jsmscp.dr.entity.DrugStockOut;
import com.jsmscp.dr.mapper.DrugBaseMapper;
import com.jsmscp.dr.mapper.DrugCatalogMapper;
import com.jsmscp.dr.mapper.DrugDeliveryMapper;
import com.jsmscp.dr.mapper.DrugStockOutItemMapper;
import com.jsmscp.dr.mapper.DrugStockOutMapper;
import com.jsmscp.dr.service.DrugCatalogService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Service
public class DrugCatalogServiceImpl implements DrugCatalogService {

    private DrugCatalogMapper drugCatalogMapper;

    private DrugStockOutItemMapper drugStockOutItemMapper;

    private DrugDeliveryMapper drugDeliveryMapper;

    private DrugStockOutMapper drugStockOutMapper;

    private DrugBaseMapper drugBaseMapper;

    @Autowired
    public DrugCatalogServiceImpl(DrugCatalogMapper drugCatalogMapper, DrugStockOutItemMapper drugStockOutItemMapper,
                                  DrugDeliveryMapper drugDeliveryMapper, DrugStockOutMapper drugStockOutMapper,
                                  DrugBaseMapper drugBaseMapper) {
        this.drugCatalogMapper = drugCatalogMapper;
        this.drugStockOutItemMapper = drugStockOutItemMapper;
        this.drugDeliveryMapper = drugDeliveryMapper;
        this.drugStockOutMapper = drugStockOutMapper;
        this.drugBaseMapper = drugBaseMapper;
    }


    /**
     * 列表查询目录管理
     *
     * @param keyword
     * @param status
     * @param pageNo
     * @return
     */
    @Override
    public HashMap<String, Object> list(String keyword, Integer storeId, Byte status, Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNo);
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        List<DrugCatalogDto> list = drugCatalogMapper.findDrugCatalog(keyword, storeId, status, pageNo, pageSize);
        int count = drugCatalogMapper.findCount(keyword, storeId, status);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        map.put("list", list);
        map.put("pageSize", Constant.DEFAULT_PAGE_SIZE);
        return map;
    }

    /**
     * 根据id查询药品目录
     *
     * @param drugCatalogId
     * @return
     */

    @Override
    public DrugCatalogDto findOne(Long drugCatalogId) {
        return drugCatalogMapper.findOne(drugCatalogId);
    }


    /**
     * 添加药品目录信息
     *
     * @param drugId
     * @param storeId
     * @param drugCode
     * @param unit
     * @param goodName
     * @param maxOnceNumber
     * @param lowerLimit
     * @param upperLimit
     * @param status
     * @param onceUnit
     * @param onceNumber
     * @param freqName
     * @param freqCode
     * @param direction
     * @return
     */
    @Override
    public Long add(Long drugId, Integer storeId, String drugCode, String unit, String goodName, Integer maxOnceNumber,
                    Integer lowerLimit, Integer upperLimit, Byte status, String onceUnit, String onceNumber,
                    String freqName, String freqCode, String direction) {
        DrugCatalog drugCatalog = new DrugCatalog();
        drugCatalog.setDrugId(drugId);
        drugCatalog.setStatus(status);
        drugCatalog.setDrugCode(drugCode);
        drugCatalog.setGoodName(goodName);
        drugCatalog.setStoreId(storeId);
        drugCatalog.setUnit(unit);
        drugCatalog.setLowerLimit(lowerLimit);
        drugCatalog.setUpperLimit(upperLimit);
        drugCatalog.setMaxOnceNumber(maxOnceNumber);
        drugCatalog.setOnceUnit(onceUnit);
        drugCatalog.setOnceNumber(onceNumber);
        drugCatalog.setFreqName(freqName);
        drugCatalog.setFreqCode(freqCode);
        drugCatalog.setDirection(direction);
        drugCatalog.setCreateAt(new Date());
        drugCatalog.setUpdateAt(new Date());
        drugCatalogMapper.insert(drugCatalog);
        return drugCatalog.getId();
    }


    /**
     * 修改药品目录信息
     *
     * @param drugCatalogId
     * @param maxOnceNumber
     * @param lowerLimit
     * @param upperLimit
     * @param status
     */
    @Override
    public void edit(Long drugCatalogId, Integer maxOnceNumber, Integer lowerLimit, Integer upperLimit, Byte status) {
        DrugCatalog drugCatalog = new DrugCatalog();
        drugCatalog.setId(drugCatalogId);
        drugCatalog.setStatus(status);
        drugCatalog.setLowerLimit(lowerLimit);
        drugCatalog.setUpperLimit(upperLimit);
        drugCatalog.setMaxOnceNumber(maxOnceNumber);
        drugCatalog.setUpdateAt(new Date());
        drugCatalogMapper.updateByPrimaryKeySelective(drugCatalog);
    }


    /**
     * 查询药品库存
     *
     * @param storeId
     * @param keyword
     * @param pageNo
     * @return
     */
    @Override
    public HashMap<String, Object> findStock(String storeId, String keyword, Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        Integer pageSize = Constant.DEFAULT_PAGE_SIZE;
        map.put("pageSize", pageSize);
        map.put("pageNo", pageNo);
        pageNo = (pageNo - 1) * pageSize;
        List<DrugCatalogDto> list = drugCatalogMapper.findStock(storeId, keyword, pageNo, pageSize);
        Integer count = drugCatalogMapper.findDrugStockCount(storeId, keyword);
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
     * 根据药店查询未对码的药品
     *
     * @param storeId
     * @return
     */
    @Override
    public List<DrugCatalog> findByDrugId(Integer storeId, String goodName) {
        List<DrugCatalog> list = drugCatalogMapper.findByDrugId(storeId, goodName);
        return list;
    }


    /**
     * 手动对码
     *
     * @param drugCode
     * @param drugId
     * @param catalogId
     */
    @Override
    public void compareDrug(String drugCode, Long drugId, Integer storeId, Integer conversionRatio, Long catalogId) {
        //查出所有属于该药店的出库药品的未对码信息
        List<DrugStockOut> drugStockOutList = drugStockOutMapper.findNoDrugId(storeId);
        for (DrugStockOut dso : drugStockOutList) {
            //根据药店药品添加出库药品的对码
            drugStockOutItemMapper.compareDrug(drugId, dso.getId(), drugCode);
        }
        List<DrugDelivery> drugDeliverList = drugDeliveryMapper.findNoDrugId(storeId); //查询所有入库的未对码药品
        for (DrugDelivery ddl : drugDeliverList) {
            drugDeliveryMapper.compareDrug(drugCode, ddl.getId(), drugId); //修改对码信息
        }
        DrugBaseDto drugBase = drugBaseMapper.selectByDrugId(drugId);
        //修改药品目录的药品对码信息
        DrugCatalog drugCatalog = new DrugCatalog();
        drugCatalog.setDrugCode(drugCode);
        drugCatalog.setIsEmergent(Constant.IS_EMERGENT_FALSE);
        drugCatalog.setConversionRatio(conversionRatio);
        drugCatalog.setDrugId(drugId);
        drugCatalog.setId(catalogId);
        drugCatalog.setInsuranceId(drugBase.getInsuranceId());
        drugCatalogMapper.updateByPrimaryKeySelective(drugCatalog);
    }


    /**
     * 解除对码
     * @param drugCode
     * @param storeId
     */
    @Override
    public void delCompare(String drugCode, Integer storeId) {
        //根据药品编码查询出所有属于该药店的药品信息
        List<DrugStockOut> drugStockOutList = drugStockOutMapper.findByDrugId(drugCode, storeId);
        for (DrugStockOut dso : drugStockOutList) {
            drugStockOutItemMapper.delDrug(dso.getId(), drugCode);
        }
        List<DrugDelivery> drugDeliverList = drugDeliveryMapper.findByDrugId(drugCode, storeId);
        for (DrugDelivery ddl : drugDeliverList) {
            drugDeliveryMapper.delDrug(drugCode, ddl.getId());
        }
        drugCatalogMapper.delDrug(drugCode, storeId);
    }


    /**
     * 查询已对码药品信息
     * @param catalogId
     * @return
     */
    @Override
    public HashMap<String, Object> findCompared(Long catalogId) {
        HashMap<String, Object> map = new HashMap<>();
        DrugCatalogDto drugCatalog = drugCatalogMapper.findCatalog(catalogId);
        DrugBaseDto drugBase = drugBaseMapper.selectByDrugId(drugCatalog.getDrugId());
        map.put("drugCatalog", drugCatalog);
        map.put("drugBase", drugBase);
        return map;
    }

    /**
     * 根据关键字查询前十条药品目录
     * @param keyword
     * @param storeId
     * @return
     */

    @Override
    public List<String> findAllCatalogByKey(String keyword, Integer storeId) {
        List<String> names = drugCatalogMapper.findAllCatalogByKey(keyword, storeId, Constant.ZERO_SIZE,
                Constant.DROP_DOWN_LIST_SIZE);
        return names;
    }

    /**
     * 药店端库存管理数据导出
     * @param keyword
     * @return
     */
    @Override
    public List<DrugCatalogDto> exportStock(String keyword) {
       List<DrugCatalogDto> list = drugCatalogMapper.exportStock(keyword);
       return list;
    }

    /**
     * 根据现有出入库计算校验库存
     * @param storeId
     */
    @Override
    public void compareStock(Integer storeId) {
        List<DrugDeliverDto> deliveryList = drugDeliveryMapper.findByStoreId(storeId);
        List<DrugStockOutDto> stockOutList = drugStockOutMapper.findByStoreId(storeId);
        List<DrugCatalog> drugCatalogList = drugCatalogMapper.findByStoreId(storeId);
        for (DrugDeliverDto ddd : deliveryList) {
            for (DrugCatalog dcl : drugCatalogList) {
                if (Objects.equals(ddd.getDrugCatalogId(), dcl.getId())) {
                    dcl.setStock(ddd.getQuantity().add(dcl.getInitStock().setScale(3, BigDecimal.ROUND_DOWN)));
                }
            }
        }
        for (DrugStockOutDto sol : stockOutList) {
            for (DrugCatalog dcl : drugCatalogList) {
                if (Objects.equals(sol.getDrugCatalogId(), dcl.getId())){
                    dcl.setStock(dcl.getInitStock().subtract(sol.getQuantity()
                            .setScale(3,BigDecimal.ROUND_DOWN)));
                }
            }
        }
        for (DrugCatalog dc: drugCatalogList) {
            drugCatalogMapper.updateStock(dc.getStock(), dc.getId());
        }
    }

}
