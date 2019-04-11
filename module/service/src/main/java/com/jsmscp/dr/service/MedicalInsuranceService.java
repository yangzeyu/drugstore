package com.jsmscp.dr.service;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.entity.MedicalInsurance;

import java.util.HashMap;
import java.util.List;

public interface MedicalInsuranceService {


    /**
     * 医保停用
     * @param id
     * @param status
     * @return
     */
    void changeStatus(Long id, Byte status) throws BusinessException;



    /**
     * 选择性添加医保信息
     * @param
     * @return
     */
    Long add(String medicalInsuranceCode, String threeDirectoryName, String threeDirectoryType,
               Byte collectType, String collectLvl, Byte status, String dosageForm, String spec);



    /**
     * 查询一个医保详细信息
     * @param id
     * @return
     */
    MedicalInsurance findOne(Long id);

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
    void edit(Long id, String medicalInsuranceCode, String threeDirectoryName, String threeDirectoryType,
               Byte collectType, String collectLvl, String dosageForm, String spec);

    /**
     * 查询目录显示(分页)
     * @return
     */
    HashMap<String, Object> list(String keyword, String collectType, String collectLvl,
                                                   Integer pageNo);

    /**
     * 查询所有的等级
     * @return
     */
    List<MedicalInsurance> findAllGrade();

    void saveDrug(MedicalInsurance mi) throws BusinessException;

    List<MedicalInsurance> findAllInsurance();

    /**
     * 查询所有的医保信息
     * @return
     */
    List<MedicalInsurance> findAllMedicalInsuranceCode(String keyword);

    /**
     * 手工对码
     * @param medicalInsuranceId
     * @param drugId
     */
    void compareInsurance(Long medicalInsuranceId, Long drugId);

    /**
     * 解除对码
     * @param drugId
     */
    void delCompare(Long drugId);

    /**
     * 查询医保对码信息
     * @param drugId
     * @return
     */
    HashMap<String, Object> findCompared(Long drugId);

    /**
     * 查询医保目录信息
     * @param keyword
     * @param collectType
     * @param collectLvl
     * @return
     */
    List<MedicalInsurance> findInsuranceCompare(String keyword, String collectType, String collectLvl);

    /**
     * 查询平台医保目录信息(下拉框)
     * @param keyword
     * @return
     */
    List<MedicalInsurance> findAllInsuranceByKey(String keyword);
}
