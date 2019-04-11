package com.jsmscp.dr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.constant.ErrorCodeEnum;
import com.jsmscp.dr.dto.DrugBaseDto;
import com.jsmscp.dr.entity.DrugBase;
import com.jsmscp.dr.mapper.DrugBaseMapper;
import com.jsmscp.dr.service.DrugBaseService;
import com.jsmscp.dr.util.PinyinUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class DrugBaseServiceImpl implements DrugBaseService {

    private DrugBaseMapper drugBaseMapper;

    @Autowired
    public DrugBaseServiceImpl(DrugBaseMapper drugBaseMapper) {
        this.drugBaseMapper = drugBaseMapper;
    }

    /**
     * 查看药品基本信息列表
     * @param keyword
     * @param type
     * @param permissionNum
     * @param pageNo
     * @param manufactureId
     * @return
     */
    @Override
    public HashMap<String, Object> list(String keyword, Byte type, String permissionNum, Integer pageNo,
                                        Integer manufactureId, Long medicalInsuranceId)
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNo);
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        List<DrugBaseDto> list = drugBaseMapper.findDrugBase(keyword, permissionNum, type, manufactureId, pageNo,
                pageSize, medicalInsuranceId);
        int count = drugBaseMapper.findCount(keyword, permissionNum, type, manufactureId, medicalInsuranceId);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        map.put("list", list);
        map.put("pageSize", Constant.DEFAULT_PAGE_SIZE);
        return map;
    }

    /**
     * 根据id查询药品信息
     * @param drugBaseId
     * @return
     */
    @Override
    public DrugBaseDto findOne(Long drugBaseId) {
        DrugBaseDto drugBase = drugBaseMapper.selectByDrugId(drugBaseId);
        return drugBase;
    }

    /**
     * 添加药品信息
     * @param name
     * @param goodName
     * @param dosageForm
     * @param type
     * @param spec
     * @param isMedicalInsurance
     * @param unit
     * @param standSpecRate
     * @param unitPrice
     * @param retailPrice
     * @param permissionNum
     * @param manufactureId
     * @return
     * @throws BusinessException
     */
    @Override
    public Long add(String name, String goodName, String dosageForm, Byte type, String spec, Byte isMedicalInsurance,
                    String unit, Byte status, Integer standSpecRate, BigDecimal unitPrice, BigDecimal retailPrice,
                    String permissionNum, Integer manufactureId, Long medicalInsuranceId) throws BusinessException {
        DrugBase db = drugBaseMapper.findDrug(goodName, dosageForm, spec, unit, manufactureId);
        if (db == null) {
            DrugBase drugBase = new DrugBase();
            drugBase.setName(name);
            if (drugBase.getName() != null || !"".equals(drugBase.getName())) {
                drugBase.setNamePinyin(PinyinUtil.changeToGetShortPinYin(drugBase.getName()).toUpperCase());
            }
            drugBase.setGoodName(goodName);
            if (drugBase.getGoodName() != null || !"".equals(drugBase.getGoodName())) {
                drugBase.setGoodNamePinyin(PinyinUtil.changeToGetShortPinYin(drugBase.getGoodName()).toUpperCase());
            }
            drugBase.setDosageForm(dosageForm);
            drugBase.setType(type);
            drugBase.setSpec(spec);
            drugBase.setUnit(unit);
            drugBase.setStandSpecRate(standSpecRate);
            drugBase.setUnitPrice(unitPrice);
            drugBase.setRetailPrice(retailPrice);
            drugBase.setStatus(status);
            drugBase.setIsMedicalInsurance(isMedicalInsurance);
            drugBase.setPermissionNumber(permissionNum);
            drugBase.setManufactureId(manufactureId);
            drugBase.setInsuranceId(medicalInsuranceId);
            drugBase.setCreateAt(new Date());
            drugBase.setUpdateAt(new Date());
            drugBaseMapper.insert(drugBase);
            return drugBase.getId();
        } else {
            throw new BusinessException(ErrorCodeEnum.DRUG_EXIST.getErrorMessage());
        }
    }

    /**
     * 修改药品信息
     * @param drugBaseId
     * @param name
     * @param goodName
     * @param dosageForm
     * @param type
     * @param spec
     * @param unit
     * @param standSpecRate
     * @param unitPrice
     * @param retailPrice
     * @param permissionNum
     * @param manufactureId
     * @throws BusinessException
     */
    @Override
    public void edit(Long drugBaseId, String name, String goodName, String dosageForm, Byte type, String spec,
                     String unit, Byte status, Integer standSpecRate, BigDecimal unitPrice, BigDecimal retailPrice,
                     String permissionNum, Integer manufactureId, Long medicalInsuranceId) throws BusinessException {
        DrugBase db = drugBaseMapper.findDrug(goodName, dosageForm, spec, unit, manufactureId);
        if (db == null || db.getId().equals(drugBaseId)) {
            DrugBase drugBase = new DrugBase();
            drugBase.setId(drugBaseId);
            drugBase.setName(name);
            if (drugBase.getName() != null || !"".equals(drugBase.getName())) {
                drugBase.setNamePinyin(PinyinUtil.changeToGetShortPinYin(drugBase.getName()).toUpperCase());
            }
            drugBase.setGoodName(goodName);
            if (drugBase.getGoodName() != null || !"".equals(drugBase.getGoodName())) {
                drugBase.setGoodNamePinyin(PinyinUtil.changeToGetShortPinYin(drugBase.getGoodName()).toUpperCase());
            }
            drugBase.setDosageForm(dosageForm);
            drugBase.setType(type);
            drugBase.setSpec(spec);
            drugBase.setUnit(unit);
            drugBase.setStatus(status);
            drugBase.setStandSpecRate(standSpecRate);
            drugBase.setUnitPrice(unitPrice);
            drugBase.setInsuranceId(medicalInsuranceId);
            drugBase.setRetailPrice(retailPrice);
            drugBase.setPermissionNumber(permissionNum);
            drugBase.setManufactureId(manufactureId);
            drugBase.setUpdateAt(new Date());
            drugBaseMapper.updateByPrimaryKeySelective(drugBase);
        } else {
        throw new BusinessException(ErrorCodeEnum.DRUG_EXIST.getErrorMessage());
        }
    }

    /**
     * 查询所有药品
     * @return
     */
    @Override
    public List<DrugBase> findAllDrug() {
        List<DrugBase> list = drugBaseMapper.findAllDrug();
        return list;
    }

    /**
     * 根据关键词或生产厂家查询药品
     * @param keyword
     * @param manufactureId
     * @return
     */
    @Override
    public List<DrugBaseDto> findAllDrugBase(String keyword, Integer manufactureId) {
        List<DrugBaseDto> list = drugBaseMapper.findAllDrugBase(keyword, manufactureId);
        return list;
    }

    /**
     * 保存药品基本信息
     * @param drugBase
     * @throws BusinessException
     */
    @Override
    public void saveDrug(DrugBase drugBase) throws BusinessException {
        int num = drugBaseMapper.findByCode(drugBase.getPlatformCode());
        if (num == 0 ) {
            drugBaseMapper.insert(drugBase);
        } else {
            throw new BusinessException(drugBase.getName() + "---药品已存在");
        }
    }


    /**
     * 手工对码查询药品医保信息(不需要分页！)
     * @param keyword
     * @param manufactureId
     * @return
     */
    @Override
    public List<DrugBase> findAllDrugInsurance(String keyword, Integer manufactureId) {
        List<DrugBase> list = drugBaseMapper.selectDrugInsurance(keyword, manufactureId);
        return list;
    }

    @Override
    public List<String> findAllDrugByKey(String keyword) {
        List<String> names = drugBaseMapper.findAllDrugByKey(keyword, Constant.ZERO_SIZE, Constant.DROP_DOWN_LIST_SIZE);
        return names;
    }
}
