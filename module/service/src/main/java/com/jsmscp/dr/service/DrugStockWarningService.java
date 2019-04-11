package com.jsmscp.dr.service;

import com.jsmscp.dr.dto.DrugStockWarningDto;

import java.util.List;

public interface DrugStockWarningService {
    List<DrugStockWarningDto> findAllWarning();
}
