package com.jsmscp.dr.service;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.dto.DrugBaseDto;
import com.jsmscp.dr.entity.DrugBase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface DrugBaseService {
    HashMap<String, Object> list(String keyword, Byte type, String permissionNum, Integer pageNo,
                                Integer manufactureId, Long medicalInsuranceId);

    DrugBaseDto findOne(Long drugBaseId);

    Long add(String name, String goodName, String dosageForm, Byte type, String spec, Byte isMedicalInsurance,
             String unit, Byte status, Integer standSpecRate, BigDecimal unitPrice, BigDecimal retailPrice,
             String permissionNum, Integer manufactureId, Long medicalInsuranceId) throws BusinessException;

    void edit(Long drugBaseId, String name, String goodName, String dosageForm, Byte type, String spec, String unit,
              Byte status, Integer standSpecRate, BigDecimal unitPrice, BigDecimal retailPrice, String permissionNum,
              Integer manufactureId, Long medicalInsuranceId) throws BusinessException;

    List<DrugBase> findAllDrug();

    List<DrugBaseDto> findAllDrugBase(String keyword, Integer manufactureId);

    void saveDrug(DrugBase drugBase) throws BusinessException;

    List<DrugBase> findAllDrugInsurance(String keyword, Integer manufactureId);

    List<String> findAllDrugByKey(String keyword);
}
