package com.jsmscp.dr.controller.api;


import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.entity.MedicalInsurance;
import com.jsmscp.dr.service.MedicalInsuranceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.dto.DrugBaseDto;
import com.jsmscp.dr.entity.DrugBase;
import com.jsmscp.dr.entity.Manufacture;
import com.jsmscp.dr.service.DrugBaseService;
import com.jsmscp.dr.service.ManufactureService;
import com.jsmscp.dr.util.ExcelUtil;
import com.jsmscp.dr.util.Response;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/drugBase")
public class DrugBaseController {

    private static final Logger LOG = LoggerFactory.getLogger(DrugBaseController.class);

    private DrugBaseService drugBaseService;

    private ManufactureService manufactureService;

    private MedicalInsuranceService medicalInsuranceService;

    @Autowired
    public DrugBaseController(DrugBaseService drugBaseService, ManufactureService manufactureService,
                              MedicalInsuranceService medicalInsuranceService) {
        this.drugBaseService = drugBaseService;
        this.manufactureService = manufactureService;
        this.medicalInsuranceService = medicalInsuranceService;
    }


    /**
     * 列表显示菜单
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list(@RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "type", required =  false) Byte type,
                         @RequestParam(value = "permissionNum", required =  false) String permissionNum,
                         @RequestParam(value = "pageNo", required = false) Integer pageNo,
                         @RequestParam(value = "manufactureId", required = false) Integer manufactureId,
                         @RequestParam(value = "medicalInsuranceId", required = false) Long medicalInsuranceId)
    {
        HashMap<String, Object> map = drugBaseService.list(keyword, type, permissionNum, pageNo, manufactureId,
                medicalInsuranceId);
        return Response.buildSuccess(map);
    }

    /**
     * 单个查询药品信息.
     * @param drugBaseId
     * @return
     */
    @RequestMapping(value = "findOne", method = RequestMethod.GET)
    public Response findOne(@RequestParam(value = "drugBaseId", required =  false) Long drugBaseId) {
        DrugBaseDto drugBase = drugBaseService.findOne(drugBaseId);
        return Response.buildSuccess(drugBase);
    }

    /**
     * 新增药品.
     * @param name
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Response add(@RequestParam(value = "name") String name,
                        @RequestParam(value = "goodName") String goodName,
                        @RequestParam(value = "dosageForm") String dosageForm,
                        @RequestParam(value = "type") Byte type,
                        @RequestParam(value = "spec") String spec,
                        @RequestParam(value = "isMedicalInsurance") Byte isMedicalInsurance,
                        @RequestParam(value = "unit") String unit,
                        @RequestParam(value = "status") Byte status,
                        @RequestParam(value = "standSpecRate") Integer standSpecRate,
                        @RequestParam(value = "unitPrice", required = false) BigDecimal unitPrice,
                        @RequestParam(value = "retailPrice", required = false) BigDecimal retailPrice,
                        @RequestParam(value = "permissionNum", required = false) String permissionNum,
                        @RequestParam(value = "medicalInsuranceId", required = false) Long medicalInsuranceId,
                        @RequestParam(value = "manufactureId") Integer manufactureId) {
        try {
            Long  id = drugBaseService.add(name, goodName, dosageForm, type, spec, isMedicalInsurance, unit, status,
                    standSpecRate, unitPrice, retailPrice, permissionNum, manufactureId, medicalInsuranceId);
            return Response.buildSuccess(id);
        } catch (BusinessException e) {
            e.printStackTrace();
            LOG.info("", e);
            return Response.fail(e.getMessage());
        }
    }


    /**r
     * 修改药品.
     * @param name
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Response edit(@RequestParam(value = "drugBaseId") Long drugBaseId,
                         @RequestParam(value = "name") String name,
                         @RequestParam(value = "goodName") String goodName,
                         @RequestParam(value = "dosageForm") String dosageForm,
                         @RequestParam(value = "type") Byte type,
                         @RequestParam(value = "spec") String spec,
                         @RequestParam(value = "unit") String unit,
                         @RequestParam(value = "status") Byte status,
                         @RequestParam(value = "standSpecRate", required = false) Integer standSpecRate,
                         @RequestParam(value = "unitPrice", required = false) BigDecimal unitPrice,
                         @RequestParam(value = "retailPrice", required = false) BigDecimal retailPrice,
                         @RequestParam(value = "permissionNum", required = false) String permissionNum,
                         @RequestParam(value = "medicalInsuranceId", required = false) Long medicalInsuranceId,
                         @RequestParam(value = "manufactureId") Integer manufactureId) {
        try {
            drugBaseService.edit(drugBaseId, name, goodName, dosageForm, type, spec, unit, status, standSpecRate,
                    unitPrice, retailPrice, permissionNum, manufactureId, medicalInsuranceId);
            return Response.buildSuccess("success");
        } catch (BusinessException e) {
            LOG.info("", e);
            return Response.fail(e.getMessage());
        }
    }

    /**
     * 药品信息上传
     * @return
     */
    @RequestMapping(value = "exportDrugBaseModel", method = RequestMethod.POST)
    public Response exportDrugBaseModel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        HashMap<String, Object> resultMsg = new HashMap<>();
        List<String> msg = new ArrayList<>();
        List<DrugBase> list;  //表格中的药品数据
        List<String> msgList;  //生产厂家失效信息
        HashMap<String, Integer> manufactureMap = new HashMap<>();  //上产厂家map
        HashMap<String, Long> medicalInsuranceMap = new HashMap<>();
        List<Manufacture> manufactureList = manufactureService.findAllManufacture();
        List<MedicalInsurance> medicalInsuranceList = medicalInsuranceService.findAllInsurance();
        for (Manufacture mfl: manufactureList) {
            manufactureMap.put(mfl.getName(), mfl.getId());
        }
        for (MedicalInsurance mcis: medicalInsuranceList) {
            medicalInsuranceMap.put(mcis.getMedicalInsuranceCode(), mcis.getId());
        }
        if (!file.isEmpty()) {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            if (suffix.equals(".xls") || suffix.equals(".xlsx")) {
                //获取表格数据
                try {
                    HashMap <String, Object> backMap = ExcelUtil.readDrugBaseExcel(file.getInputStream(),
                            manufactureMap, medicalInsuranceMap);
                    list = (List<DrugBase>) backMap.get(Constant.BACK_STATUS_SUCCESS);
                    msgList = (List<String>) backMap.get(Constant.BACK_STATUS_ERROR);
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                    return Response.fail("填写Excel格式有误");
                }
                //存入数据
                if (list != null) {
                    msg = new ArrayList<>();
                    for (DrugBase drug : list) {
                        //去重
                        try {
                            drugBaseService.saveDrug(drug);
                        } catch (BusinessException e) {
                             msg.add(e.getMessage());
                        }
                    }
                }
            } else {
                return Response.fail("导入的不是Excel文件");
            }
        } else {
            return Response.fail("请选择文件");
        }
        resultMsg.put("msg", msg);
        resultMsg.put("msgList", msgList);
        return Response.buildSuccess(resultMsg);
    }




}
