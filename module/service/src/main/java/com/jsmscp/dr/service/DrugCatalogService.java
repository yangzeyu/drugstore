package com.jsmscp.dr.service;

import com.jsmscp.dr.dto.DrugCatalogDto;
import com.jsmscp.dr.entity.DrugCatalog;

import java.util.HashMap;
import java.util.List;

public interface DrugCatalogService {


    /**
     * 列表查询药品目录
     * @param keyword
     * @param status
     * @param pageNo
     * @return
     */
    HashMap<String, Object> list(String keyword, Integer storeId, Byte status, Integer pageNo);


    /**
     * 单个查询
     * @param drugCatalogId
     * @return
     */
    DrugCatalogDto findOne(Long drugCatalogId);


    /**
     * 新增药品目录
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
    Long add(Long drugId, Integer storeId, String drugCode, String unit, String goodName, Integer maxOnceNumber,
             Integer lowerLimit, Integer upperLimit, Byte status, String onceUnit, String onceNumber, String freqName,
             String freqCode, String direction);

    /**
     * 修改药品目录
     * @param drugCatalogId
     * @param maxOnceNumber
     * @param lowerLimit
     * @param upperLimit
     * @param status
     */
    void edit(Long drugCatalogId, Integer maxOnceNumber, Integer lowerLimit, Integer upperLimit, Byte status);


    /**
     * 查询库存
     * @param storeId
     * @param keyword
     * @param pageNo
     * @return
     */
    HashMap<String, Object> findStock(String storeId, String keyword, Integer pageNo);

    /**
     * 根据药店查询没有对上码的药品
     * @param storeId
     * @param goodName
     * @return
     */
    List<DrugCatalog> findByDrugId(Integer storeId, String goodName);

    /**
     * 手动对码
     * @param drugCode
     * @param drugId
     * @param catalogId
     */
    void compareDrug(String drugCode, Long drugId, Integer storeId, Integer conversionRatio, Long catalogId);

    void delCompare(String drugCode, Integer storeId);

    HashMap<String, Object> findCompared(Long catalogId);

    List<String> findAllCatalogByKey(String keyword, Integer storeId);


    /**
     * 查询库存数据导出
     * @param keyword
     * @return
     */
    List<DrugCatalogDto> exportStock(String keyword);

    void compareStock(Integer storeId);
}
