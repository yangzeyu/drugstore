package com.jsmscp.dr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.dto.DrugInvoiceDto;
import com.jsmscp.dr.mapper.DrugInvoiceMapper;
import com.jsmscp.dr.service.DrugInvoiceService;

import java.util.List;

@Service
public class DrugInvoiceServiceImpl implements DrugInvoiceService {
    private DrugInvoiceMapper drugInvoiceMapper;

    @Autowired
    public DrugInvoiceServiceImpl(DrugInvoiceMapper drugInvoiceMapper) {
        this.drugInvoiceMapper = drugInvoiceMapper;
    }


    /**
     * 查询进货明细
     * @param deliverId
     * @return
     */
    @Override
    public List<DrugInvoiceDto> findInvoiceItem(Integer deliverId) {
        List<DrugInvoiceDto> list = drugInvoiceMapper.findInvoiceItem(deliverId);
        return list;
    }
}
