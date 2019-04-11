package com.jsmscp.dr.controller.api;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.entity.MedicalInsurance;
import com.jsmscp.dr.service.MedicalInsuranceService;
import com.jsmscp.dr.util.ExcelUtil;
import com.jsmscp.dr.util.Response;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/medicalInsurance")
public class MedicalInsuranceController {

    private static final Logger log = LoggerFactory.getLogger(MedicalInsuranceController.class);

    private MedicalInsuranceService medicalInsuranceService;

    @Autowired
    public void setMedicalInsuranceService(MedicalInsuranceService medicalInsuranceService) {
        this.medicalInsuranceService = medicalInsuranceService;
    }


    /**
     * 医保停用（修改状态）
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "changeStatus", method = RequestMethod.POST)
    public Response changeStatus(@RequestParam(value = "id") Long id, @RequestParam(value = "status") Byte status) {
        try {
            medicalInsuranceService.changeStatus(id, status);
            return Response.buildSuccess("success");
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response.fail(e.getMessage());
        }
    }


    /**
     * 添加医保信息(部分添加)
     * @param medicalInsuranceCode
     * @param threeDirectoryName
     * @param threeDirectoryType
     * @param collectType
     * @param collectLvl
     * @param dosageForm
     * @param spec
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Response add(@RequestParam(value = "medicalInsuranceCode") String medicalInsuranceCode,
                           @RequestParam(value = "threeDirectoryName") String threeDirectoryName,
                           @RequestParam(value = "threeDirectoryType") String threeDirectoryType,
                           @RequestParam(value = "collectType") Byte collectType,
                           @RequestParam(value = "collectLvl") String collectLvl,
                           @RequestParam(value = "status") Byte status,
                           @RequestParam(value = "dosageForm") String dosageForm,
                           @RequestParam(value = "spec") String spec) {
        Long id =  medicalInsuranceService.add(medicalInsuranceCode, threeDirectoryName, threeDirectoryType,
                collectType, collectLvl, status, dosageForm, spec);
        return Response.buildSuccess(id);
    }

    /**
     * 查询单个医保信息
     * @param id
     * @return
     */
    @RequestMapping(value = "findOne", method = RequestMethod.GET)
    public Response findOne(@RequestParam(value = "id") Long id) {
        MedicalInsurance  medicalInsurance = medicalInsuranceService.findOne(id);
        return Response.buildSuccess(medicalInsurance);
    }

    /**
     * 更新医保信息(停用医保信息)
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
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Response edit(@RequestParam(value = "id") Long id,
                                       @RequestParam(value = "medicalInsuranceCode") String medicalInsuranceCode,
                                       @RequestParam(value = "threeDirectoryName") String threeDirectoryName,
                                       @RequestParam(value = "threeDirectoryType") String threeDirectoryType,
                                       @RequestParam(value = "collectType") Byte collectType,
                                       @RequestParam(value = "collectLvl") String collectLvl,
                                       @RequestParam(value = "dosageForm") String dosageForm,
                                       @RequestParam(value = "spec") String spec) {

        medicalInsuranceService.edit(id, medicalInsuranceCode, threeDirectoryName, threeDirectoryType,
                collectType, collectLvl, dosageForm, spec);
        return Response.buildSuccess("success");
    }


    /**
     * 查询医保信息（分页）
     * @param keyword
     * @param collectType
     * @param collectLvl
     * @param pageNo
     * @return
     */

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "collectType", required = false) String collectType,
            @RequestParam(value = "collectLvl", required = false) String collectLvl,
            @RequestParam(value = "pageNo") Integer pageNo ) {

        HashMap<String, Object> map = medicalInsuranceService.list(keyword,
                collectType, collectLvl, pageNo);
        return Response.buildSuccess(map);
    }


    /**
     * 医保目录信息上传
     * @return
     */
    @RequestMapping(value = "exportInsuranceModel", method = RequestMethod.POST)
    public Response exportInsuranceModel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        List<MedicalInsurance> list;
        List<String> msg = new ArrayList<>();
        if (!file.isEmpty()) {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            if (suffix.equals(".xls") || suffix.equals(".xlsx")) {
                try {
                    list = ExcelUtil.readMedicalInsurance(file.getInputStream());
                } catch (Exception e) {
                    return Response.fail("填写Excel格式有误");
                }
            } else {
                return Response.fail("导入的不是Excel文件");
            }
            if (list != null) {
                for (MedicalInsurance mi : list) {
                    try {
                        medicalInsuranceService.saveDrug(mi);
                    } catch (BusinessException e) {
                        msg.add(e.getMessage());
                    }
                }
            }
        } else {
            return Response.fail("请选择文件");
        }
        return Response.buildSuccess(msg);
    }

}




