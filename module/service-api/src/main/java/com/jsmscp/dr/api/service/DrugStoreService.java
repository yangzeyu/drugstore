package com.jsmscp.dr.api.service;

import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.mapper.DrugStoreMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 类实现缓存化
 */
@Service
@CacheConfig(cacheNames = {"drugStoreCache"})
public class DrugStoreService {

    private static final Logger logger = LoggerFactory.getLogger(DrugStoreService.class);

    private DrugStoreMapper drugStoreMapper;

    @Autowired
    public DrugStoreService(DrugStoreMapper drugStoreMapper) {
        this.drugStoreMapper = drugStoreMapper;
    }

    @Cacheable(key = "#appCode")
    public DrugStore findByAppCode(String appCode) {
        logger.info("fetch from database");
        return drugStoreMapper.findByAppCode(appCode);
    }

}
