package com.jsmscp.dr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.entity.ApiOperateLog;
import com.jsmscp.dr.mapper.ApiOperateLogMapper;
import com.jsmscp.dr.service.ApiOperateLogService;

import java.util.HashMap;
import java.util.List;

@Service
public class ApiOperateServiceImpl implements ApiOperateLogService {

    private ApiOperateLogMapper apiOperateLogMapper;

    @Autowired
    public ApiOperateServiceImpl(ApiOperateLogMapper apiOperateLogMapper) {
        this.apiOperateLogMapper = apiOperateLogMapper;
    }

    /**
     * 查询日志接口
     * @param operator
     * @param url
     * @param status
     * @param startTime
     * @param endTime
     * @param pageNo
     * @return
     */
    @Override
    public HashMap<String, Object> list(String operator, String url, String status, String startTime, String endTime,
                                        Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        map.put("pageSize", pageSize);
        map.put("pageNo", pageNo);
        List<ApiOperateLog> list = apiOperateLogMapper.list(operator, url, status, startTime, endTime, pageNo,
                pageSize);
        Integer count = apiOperateLogMapper.findCount(operator, url, status, startTime, endTime);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        map.put("list", list);
        return map;
    }
}
