package com.jsmscp.dr.service.report.impl;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.DrugBaseReportDto;
import com.jsmscp.dr.mapper.report.DrugBaseReportMapper;
import com.jsmscp.dr.service.report.DrugBaseReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DrugBaseReportServiceImpl implements DrugBaseReportService {

    private DrugBaseReportMapper drugBaseReportMapper;

    @Autowired
    public DrugBaseReportServiceImpl(DrugBaseReportMapper drugBaseReportMapper) {
        this.drugBaseReportMapper = drugBaseReportMapper;
    }

    @Override
    public HashMap<String, Object> findDrugBaseReport(String goodName, Long manufactureId, String platFormCode,
                                                      String startTime, String endTime, Integer pageNo) {
        HashMap<String, Object> map  = new HashMap<>();
        Integer pageSize = Constant.DEFAULT_PAGE_SIZE;
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        pageNo = (pageNo - 1) * pageSize;
       List<DrugBaseReportDto> list = drugBaseReportMapper.findDrugBaseReport(goodName, manufactureId, platFormCode,
               startTime, endTime, pageNo, pageSize);
       Integer count = drugBaseReportMapper.findCount(goodName, manufactureId, platFormCode, startTime, endTime);
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
