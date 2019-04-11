package com.jsmscp.dr.service;

import com.jsmscp.dr.dto.DrugInvoiceDto;

import java.util.List;

public interface DrugInvoiceService {

    /**
     * 根据随货同行单查询发票信息
     * @param deliverId
     * @return
     */
    List<DrugInvoiceDto> findInvoiceItem(Integer deliverId);
}
