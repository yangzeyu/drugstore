package com.jsmscp.dr.service;

import com.jsmscp.dr.dto.CompareDrugCodeDto;

import java.util.List;

public interface DrugBatchNoService {
    List<CompareDrugCodeDto> findAllNoDrugId();
}
