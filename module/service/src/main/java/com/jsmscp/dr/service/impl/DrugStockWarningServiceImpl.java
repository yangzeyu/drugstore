package com.jsmscp.dr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.DrugStockWarningDto;
import com.jsmscp.dr.mapper.DrugStockWarningMapper;
import com.jsmscp.dr.service.DrugStockWarningService;

import java.util.List;

@Service
public class DrugStockWarningServiceImpl implements DrugStockWarningService {


    private DrugStockWarningMapper drugStockWarningMapper;

    @Autowired
    public DrugStockWarningServiceImpl(DrugStockWarningMapper drugStockWarningMapper) {
        this.drugStockWarningMapper = drugStockWarningMapper;
    }

    /**
     * 查询所有报警信息
     * @return
     */
    @Override
    public List<DrugStockWarningDto> findAllWarning() {
        List<DrugStockWarningDto> list = drugStockWarningMapper.findAllWarning(Constant.STOCK_WARNING_NOT_READ,
                                                                            Constant.STOCK_WARNING_READ);
        return list;
    }
}
