package com.jsmscp.dr.controller.api;

import com.jsmscp.dr.entity.MedicalInsurance;
import com.jsmscp.dr.service.DrugBaseService;
import com.jsmscp.dr.service.MedicalInsuranceService;
import com.jsmscp.dr.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/compareMedicalInsurance")
public class CompareMedicalInsuranceController {

    private MedicalInsuranceService medicalInsuranceService;

    private DrugBaseService drugBaseService;

    @Autowired
    public CompareMedicalInsuranceController(MedicalInsuranceService medicalInsuranceService,
                                             DrugBaseService drugBaseService) {
        this.medicalInsuranceService = medicalInsuranceService;
        this.drugBaseService = drugBaseService;
    }



    /**
     * 查询医保目录信息
     * @param keyword
     * @param collectType
     * @param collectLvl
     * @return
     */
    @RequestMapping(value = "findByMedicalInsurance", method = RequestMethod.GET)
    public Response findByMedicalInsurance(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "collectType", required = false) String collectType,
            @RequestParam(value = "collectLvl", required = false) String collectLvl)
    {
        List<MedicalInsurance> list = medicalInsuranceService.findInsuranceCompare(keyword, collectType, collectLvl);
        return  Response.buildSuccess(list);
    }




 /*   *//*
    @RequestMapping(value = "findByDrugBaseId", method = RequestMethod.GET)
    public Response findByDrugBaseId(@RequestParam(value = "keyword", required = false) String keyword,
                                     @RequestParam(value = "manufactureId", required = false) Integer manufactureId) {
        List<DrugBase> list = drugBaseService.findAllDrugInsurance(keyword, manufactureId);
        return Response.buildSuccess(list);
    }*/

    /**
     * 查询平台医保目录信息(下拉框)
     * @param keyword
     * @return
     */
    @RequestMapping(value = "findAllInsuranceByKey", method = RequestMethod.GET)
    public Response findAllInsuranceByKey(@RequestParam(value = "keyword") String keyword) {

       List<MedicalInsurance> list =  medicalInsuranceService.findAllInsuranceByKey(keyword);
       return Response.buildSuccess(list);
    }


    /**
     * 手工对码
     * @return
     */
    @RequestMapping(value = "compareMedicalInsurance", method = RequestMethod.POST)
    public Response compareMedicalInsurance(@RequestParam(value = "medicalInsuranceId") Long medicalInsuranceId,
                                            @RequestParam(value = "drugId") Long drugId) {
            medicalInsuranceService.compareInsurance(medicalInsuranceId, drugId);
            return Response.buildSuccess("");
    }

    /**

     * 解除对码
     * @param drugId
     * @return
     */
    @RequestMapping(value = "delCompare", method = RequestMethod.POST)
    public Response delCompare(@RequestParam(value = "drugId") Long drugId) {

            medicalInsuranceService.delCompare(drugId);
             return Response.buildSuccess("");
    }

    /**
     * 查询医保对码信息
     * @param drugId
     * @return
     */
    @RequestMapping(value = "findCompared", method = RequestMethod.GET)
    public Response findCompared(@RequestParam(value = "drugId") Long drugId) {
         HashMap<String, Object> map = medicalInsuranceService.findCompared(drugId);
        return Response.buildSuccess(map);
    }



}
