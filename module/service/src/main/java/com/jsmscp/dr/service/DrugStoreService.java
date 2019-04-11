package com.jsmscp.dr.service;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.dto.StoreOutInReportDto;
import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.entity.DrugStoreUser;

import java.util.HashMap;
import java.util.List;

public interface DrugStoreService {
    List<DrugStore> findAllStore(String keyword);

    Integer add(String storeName, String code, String address, Byte status, String contractPerson,
                String contractPhone, String contractEmail) throws BusinessException;

    void edit(Integer storeId, String storeName, String code, String address, Byte status, String contractPerson,
              String contractPhone, String contractEmail) throws BusinessException;

    HashMap<String, Object> list(String keyword, Byte status, String startTime, String endTime, Integer pageNo);

    void sendMessage(Integer drugStoreId);

    DrugStore findOne(String drugStoreId);

    List<DrugStoreUser> findStoreUser(Integer storeId);

    DrugStoreUser findUserById(Integer userId);

    HashMap<String, Object> findStoreOutIn(Integer storeId, String goodName, String startTime, String endTIme,
                                           Integer pageNo);

    List<StoreOutInReportDto> exportStore(Integer storeId, String goodName, String startTime, String endTime);
}
