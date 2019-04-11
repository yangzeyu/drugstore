package com.jsmscp.dr.api.service;

import com.jsmscp.dr.entity.ApiOperateLog;
import com.jsmscp.dr.mapper.ApiOperateLogMapper;
import com.jsmscp.dr.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class ApiLogService {

    private ApiOperateLogMapper apiOperateLogMapper;

    @Transactional
    public void create(String operator, String url, String ip, Map<String, Object> params, Integer status,
                       String respContent, Date operateDate) {
        ApiOperateLog apiOperateLog = new ApiOperateLog();
        apiOperateLog.setIp(ip);
        apiOperateLog.setStatus(status);
        apiOperateLog.setOperator(operator);
        apiOperateLog.setUrl(url);

        String reqParam = JsonUtils.toJsonString(params);

//        if (reqParam != null && reqParam.length() < 1000) {
//
//            apiOperateLog.setReqParam(reqParam);
//        }

        apiOperateLog.setRespContent(respContent);
        apiOperateLog.setOperateAt(operateDate);
        apiOperateLog.setCreateAt(new Date());
        apiOperateLog.setUpdateAt(new Date());
        apiOperateLogMapper.insertSelective(apiOperateLog);
    }

    @Autowired
    public void setApiOperateLogMapper(ApiOperateLogMapper apiOperateLogMapper) {
        this.apiOperateLogMapper = apiOperateLogMapper;
    }
}
