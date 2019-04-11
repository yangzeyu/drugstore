package com.jsmscp.dr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.CompareDrugCodeDto;
import com.jsmscp.dr.mapper.DrugCatalogMapper;
import com.jsmscp.dr.service.DrugBatchNoService;

import java.util.List;

@Service
public class DrugBatchNoServiceImpl implements DrugBatchNoService {

    private DrugCatalogMapper drugCatalogMapper;


    @Autowired
    public DrugBatchNoServiceImpl(DrugCatalogMapper drugCatalogMapper) {
        this.drugCatalogMapper = drugCatalogMapper;
    }


    /**
     * 查询所有没有对码的药品目录信息
     * @return
     */
    @Override
    public List<CompareDrugCodeDto> findAllNoDrugId() {
        List<CompareDrugCodeDto> list = drugCatalogMapper.findAllNoDrugId(Constant.STATUS_SUCCESS,
                Constant.IS_EMERGENT_TRUE);
        return list;
    }
}
