package com.jsmscp.dr.service;

import java.util.HashMap;

public interface ApiOperateLogService {
    HashMap<String, Object> list(String operator, String url, String status, String startTime, String endTime,
                 Integer pageNo);
}
