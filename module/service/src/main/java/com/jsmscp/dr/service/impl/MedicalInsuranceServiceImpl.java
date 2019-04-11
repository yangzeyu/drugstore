package com.jsmscp.dr.service.impl;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.constant.ErrorCodeEnum;
import com.jsmscp.dr.entity.DrugBase;
import com.jsmscp.dr.entity.MedicalInsurance;
import com.jsmscp.dr.mapper.DrugBaseMapper;
import com.jsmscp.dr.mapper.MedicalInsuranceMapper;
import com.jsmscp.dr.service.MedicalInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class MedicalInsuranceServiceImpl implements MedicalInsuranceService  {

    private MedicalInsuranceMapper medicalInsuranceMapper;

    private DrugBaseMapper drugBaseMapper;

    @Autowired
    public MedicalInsuranceServiceImpl(MedicalInsuranceMapper medicalInsuranceMapper, DrugBaseMapper drugBaseMapper) {
        this.medicalInsuranceMapper = medicalInsuranceMapper;
        this.drugBaseMapper = drugBaseMapper;
    }

    /**
     * 医保停用
     * @param id
     * @param status
     * @return
     */
    @Override
    public void changeStatus(Long id, Byte status) throws BusinessException {
        List<DrugBase> list = drugBaseMapper.selectByInsuranceId(id);
        if (list != null && list.size() > 0) {
            throw new BusinessException(ErrorCodeEnum.MEDICALINSURANCE_IS_USED);
        } else {
            MedicalInsurance medicalInsurance = new MedicalInsurance();
            medicalInsurance.setId(id);
            medicalInsurance.setStatus(status);
            medicalInsurance.setUpdateAt(new Date());
            medicalInsuranceMapper.updateByPrimaryKeySelective(medicalInsurance);
        }
    }

    /**
     * 添加医保信息（选择性）
     * @param medicalInsuranceCode
     * @param threeDirectoryName
     * @param threeDirectoryType
     * @param collectType
     * @param collectLvl
     * @param dosageForm
     * @param spec
     * @return
     */
    @Override
    public Long add(String medicalInsuranceCode, String threeDirectoryName, String threeDirectoryType,
                      Byte collectType, String collectLvl, Byte status, String dosageForm, String spec) {
        MedicalInsurance medicalInsurance = new MedicalInsurance();
        medicalInsurance.setMedicalInsuranceCode(medicalInsuranceCode);
        medicalInsurance.setThreeDirectoryName(threeDirectoryName);
        medicalInsurance.setThreeDirectoryType(threeDirectoryType);
        medicalInsurance.setCollectType(collectType);
        medicalInsurance.setCollectLvl(collectLvl);
        medicalInsurance.setDosageForm(dosageForm);
        medicalInsurance.setSpec(spec);
        medicalInsurance.setStatus(status);
        medicalInsurance.setCreateAt(new Date());
        medicalInsurance.setUpdateAt(new Date());
        medicalInsuranceMapper.insertSelective(medicalInsurance);
        return medicalInsurance.getId();
    }

    /**
     * 查询医保详细信息
     * @param id
     * @return
     */
    @Override
    public MedicalInsurance findOne(Long id) {

        return medicalInsuranceMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改医保信息（选择性）
     * @param id
     * @param medicalInsuranceCode
     * @param threeDirectoryName
     * @param threeDirectoryType
     * @param collectType
     * @param collectLvl
     * @param dosageForm
     * @param spec
     * @return
     */
    @Override
    public void edit(Long id, String medicalInsuranceCode, String threeDirectoryName, String threeDirectoryType,
                      Byte collectType, String collectLvl, String dosageForm, String spec) {
        MedicalInsurance medicalInsurance = new MedicalInsurance();
        medicalInsurance.setId(id);
        medicalInsurance.setMedicalInsuranceCode(medicalInsuranceCode);
        medicalInsurance.setThreeDirectoryName(threeDirectoryName);
        medicalInsurance.setThreeDirectoryType(threeDirectoryType);
        medicalInsurance.setCollectType(collectType);
        medicalInsurance.setCollectLvl(collectLvl);
        medicalInsurance.setDosageForm(dosageForm);
        medicalInsurance.setSpec(spec);
        medicalInsurance.setUpdateAt(new Date());
        medicalInsuranceMapper.updateByPrimaryKeySelective(medicalInsurance);
    }




    /**
     * 所有列表页面显示(分页)
     * @return
     */
    @Override
    public HashMap<String, Object> list(String keyword,
                                           String collectType, String collectLvl, Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNo);
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1 ) * pageSize;
        List<MedicalInsurance> list = medicalInsuranceMapper.findMedicalInsurance(keyword,
                collectType, collectLvl, pageNo, pageSize);
        int count = medicalInsuranceMapper.findMedicalInsuranceCount(keyword, collectType, collectLvl);
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
     * 查询所有的等级
     * @return
     */
    @Override
    public List<MedicalInsurance> findAllGrade() {
        return medicalInsuranceMapper.findAllGrade();
    }

    @Override
    public void saveDrug(MedicalInsurance mi) throws BusinessException {
        int num = medicalInsuranceMapper.findByCode(mi.getMedicalInsuranceCode());
        if (num == 0) {
            medicalInsuranceMapper.insert(mi);
        } else {
            throw new BusinessException(mi.getMedicalInsuranceCode() + "----目录已存在");
        }
    }

    @Override
    public List<MedicalInsurance> findAllInsurance() {
        return medicalInsuranceMapper.findAllInsurance();
    }


    /**
     *  查询医保目录信息
     * @return
     */
    @Override
    public List<MedicalInsurance> findAllMedicalInsuranceCode(String keyword) {
        return medicalInsuranceMapper.findAllMedicalInsuranceCode(keyword);
    }


    /**
     * 手工对码
     * @param medicalInsuranceId
     * @param drugId
     */
    @Override
    public void compareInsurance(Long medicalInsuranceId, Long drugId) {

            drugBaseMapper.compareInsurance(medicalInsuranceId, drugId);
    }


    /**
     * 解除对码
     * @param drugId
     */
    @Override
    public void delCompare(Long drugId) {

        drugBaseMapper.delCompare(drugId);
    }


    /**
     * 查询医保对码信息
     * @param drugId
     * @return
     */
    @Override
    public HashMap<String, Object> findCompared(Long drugId) {
        HashMap<String, Object> map = new HashMap<>();
        DrugBase drugBase = drugBaseMapper.selectByPrimaryKey(drugId);
        MedicalInsurance medicalInsurance = medicalInsuranceMapper.selectByPrimaryKey(drugBase.getInsuranceId());
        map.put("medicalInsurance", medicalInsurance);
        map.put("drugBase", drugBase);
        return map;
    }


    /**
     *  医保信息显示
     * @param keyword
     * @param collectType
     * @param collectLvl
     * @return
     */
    @Override
    public List<MedicalInsurance> findInsuranceCompare(String keyword, String collectType, String collectLvl) {
        return medicalInsuranceMapper.findInsuranceCompare(keyword, collectType, collectLvl);
    }


    /**
     * 查询医保目录平台信息（下拉框）
     * @param keyword
     * @return
     */
    @Override
    public List<MedicalInsurance> findAllInsuranceByKey(String keyword) {

       List<MedicalInsurance> list = medicalInsuranceMapper.findAllInsuranceByKey(keyword,
               Constant.ZERO_SIZE, Constant.DROP_DOWN_LIST_SIZE);
       return list;

    }


}
