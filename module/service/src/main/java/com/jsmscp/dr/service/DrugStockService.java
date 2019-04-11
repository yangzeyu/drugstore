package com.jsmscp.dr.service;

import java.util.HashMap;

public interface DrugStockService {

    HashMap<String, Object> findInAndOutItem(Long drugId, String batchNo, Integer storeId);

    HashMap<String, Object> list(Integer storeId, String drugName, Integer pageNo);

    HashMap<String, Object> catalogInList(Integer storeId, Long catalogId, Integer pageNo);
    HashMap<String, Object> catalogOutList(Integer storeId, Long catalogId, Integer pageNo);
}
