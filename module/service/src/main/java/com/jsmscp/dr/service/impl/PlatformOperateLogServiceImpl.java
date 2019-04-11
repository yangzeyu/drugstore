package com.jsmscp.dr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.entity.PlatformOperateLog;
import com.jsmscp.dr.mapper.PlatformOperateLogMapper;
import com.jsmscp.dr.service.PlatformOperateLogService;
import com.jsmscp.dr.util.JsonUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlatformOperateLogServiceImpl implements PlatformOperateLogService {

    private PlatformOperateLogMapper logMapper;

    @Autowired
    public PlatformOperateLogServiceImpl(PlatformOperateLogMapper logMapper) {
        this.logMapper = logMapper;
    }


    /**
     * 插入日志
     * @param requestURL
     * @param remoteUser
     * @param ip
     * @param map
     */
    @Override
    public void insertLogs(StringBuffer requestURL, String remoteUser, String ip, Map<String, Object> map) {
        PlatformOperateLog operateLog = new PlatformOperateLog();
        operateLog.setOperateAt(new Date());
        operateLog.setIp(ip);
        operateLog.setOperator(remoteUser);
        operateLog.setUrl(requestURL.toString());
        operateLog.setReqParam(JsonUtils.toJsonString(map));
        operateLog.setCreateAt(new Date());
        operateLog.setUpdateAt(new Date());
        logMapper.insertSelective(operateLog);

    }

    /**
     * 查询日志列表
     * @param operate
     * @param operator
     * @param startTime
     * @param endTime
     * @param pageNo
     * @return
     */
    @Override
    public HashMap<String, Object> list(String operate, String operator, String startTime, String endTime,
                                         Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNo);
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        List<PlatformOperateLog> list = logMapper.list(operate, operator, startTime, endTime, pageNo, pageSize);
        int count = logMapper.findTotal(operate, operator, startTime, endTime);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        map.put("list", list);
        map.put("pageSize", pageSize);
        return map;
    }

    /**
     * 查询所有操作人
     * @return
     */
    @Override
    public List<PlatformOperateLog> findAllOperator() {
        List<PlatformOperateLog> operateLogs = logMapper.findAllOperator();
        return operateLogs;
    }
}
