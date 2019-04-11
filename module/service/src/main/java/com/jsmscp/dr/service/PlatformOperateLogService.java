package com.jsmscp.dr.service;

import com.jsmscp.dr.entity.PlatformOperateLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PlatformOperateLogService {

    void insertLogs(StringBuffer requestURL, String remoteUser, String ip, Map<String, Object> map);

    HashMap<String, Object> list(String operate, String operator, String startTime, String endTime, Integer pageNo);

    List<PlatformOperateLog> findAllOperator();
}
