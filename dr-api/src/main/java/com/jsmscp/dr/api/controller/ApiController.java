package com.jsmscp.dr.api.controller;

import com.alibaba.fastjson.JSON;

import com.jsmscp.dr.api.common.BaseResponse;
import com.jsmscp.dr.api.service.DrugCatalogService;
import com.jsmscp.dr.api.service.DrugDeliveryInvoiceRelationService;
import com.jsmscp.dr.api.service.DrugDeliveryService;
import com.jsmscp.dr.api.service.DrugInvoiceService;
import com.jsmscp.dr.api.service.DrugStockOutService;
import com.jsmscp.dr.api.service.DrugUploadImageService;
import com.jsmscp.dr.api.service.FileService;
import com.jsmscp.dr.api.service.exception.ServiceException;
import com.jsmscp.dr.api.vo.DrugItemVO;
import com.jsmscp.dr.api.vo.DrugInfoVO;
import com.jsmscp.dr.api.vo.DrugVO;
import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.entity.DrugBase;
import com.jsmscp.dr.entity.DrugCatalog;
import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.entity.DrugUploadImage;
import com.jsmscp.dr.entity.Manufacture;
import com.jsmscp.dr.util.DateUtil;
import com.jsmscp.dr.util.StringUtils;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {

    private DrugCatalogService drugCatalogService;

    private DrugInvoiceService drugInvoiceService;

    private DrugDeliveryInvoiceRelationService drugDeliveryInvoiceRelationService;

    private DrugDeliveryService drugDeliveryService;

    private DrugStockOutService drugStockOutService;

    private FileService fileService;

    private DrugUploadImageService drugUploadImageService;

    /**
     * 药品首营登记
     */
    @PostMapping("registerDrug")
    public BaseResponse registerDrug(@RequestAttribute DrugStore store, DrugVO drug,
                                     String insuranceCode, String drugCode, BigDecimal stock)
            throws ServiceException {
        String drugName = drug.getDrugName();
        String spec = drug.getSpec();
        String manufacturerName = drug.getManufacturer();
        String unit = drug.getUnit();
        String dosageForm = drug.getDosageForm();
        BigDecimal storeStock = drug.getPresentStock();

        if (StringUtils.isBlank(drugCode)) {
            return BaseResponse.getError("药品编码不能为空");
        }
        if (StringUtils.isBlank(drugName)){
            return BaseResponse.getError("药品名称不能为空");
        }
        if (StringUtils.isBlank(spec)) {
            return BaseResponse.getError("药品规格不能为空");
        }
        if (StringUtils.isBlank(manufacturerName)) {
            return BaseResponse.getError("生产厂家不能为空");
        }
        if (StringUtils.isBlank(unit)) {
            return BaseResponse.getError("包装单位不能为空");
        }
        if (StringUtils.isBlank(dosageForm)) {
            return BaseResponse.getError("剂型不能为空");
        }
        DrugVO vo = drugCatalogService.register(drug, store, insuranceCode, drugCode, stock);

        return BaseResponse.getSuccess(vo);
    }

    /**
     * 药品查询接口
     */
    @GetMapping("queryDrug")
    public BaseResponse queryDrug(@RequestAttribute DrugStore store, String drugCode) {

        DrugCatalog catalog = drugCatalogService.findCatalog(drugCode, store.getId());
        DrugInfoVO vo = new DrugInfoVO();

        if (catalog == null) {
            return BaseResponse.getError("未查询到药品信息");
        }

        DrugBase base = catalog.getDrugBase();
        if (null != base) {
            if (base.getManufactureId() == null) {
                vo.setManufacturer(catalog.getManufacture());
            } else {
                Manufacture manufacture = drugCatalogService.selectById(base.getManufactureId());
                vo.setManufacturer(manufacture.getName());
            }
            vo.setDrugName(catalog.getGoodName());
            vo.setStatus(catalog.getStatus().intValue());
            vo.setUnit(catalog.getUnit());
            vo.setDirection(catalog.getDirection());
            vo.setFreq(catalog.getFreqName());
            vo.setUsingNumber(catalog.getOnceNumber());
            vo.setUsingUnit(catalog.getOnceUnit());
            vo.setStock(catalog.getStock());
            if (base.getStandSpecRate() != null) {
                vo.setStandSpecRate(String.valueOf(base.getStandSpecRate()));
            }
            if (base.getDosageForm() != null) {
                vo.setDosageForm(base.getDosageForm());
            }
            if (base.getSpec() != null) {
                vo.setSpec(base.getSpec());
            }
            if (base.getPlatformCode() != null) {
                vo.setPlatformCode(base.getPlatformCode());
            }
            if (base.getInsuranceId() != null) {
                vo.setInsuranceId(base.getInsuranceId());
            }
            vo.setMaxOnceNumber(String.valueOf(catalog.getMaxOnceNumber()));
        } else {
            vo.setManufacturer(catalog.getManufacture());
            vo.setDrugName(catalog.getGoodName());
            vo.setStatus(catalog.getStatus().intValue());
            vo.setUnit(catalog.getUnit());
            vo.setDirection(catalog.getDirection());
            vo.setFreq(catalog.getFreqName());
            vo.setUsingNumber(catalog.getOnceNumber());
            vo.setUsingUnit(catalog.getOnceUnit());
            vo.setStock(catalog.getStock());
            vo.setMaxOnceNumber(String.valueOf(catalog.getMaxOnceNumber()));
        }
        return BaseResponse.getSuccess(vo);
    }

    /**
     * 药品进货发票信息上传
     */
    @PostMapping("uploadInvoice")
    public BaseResponse uploadInvoice(@RequestAttribute DrugStore store, String invoiceCode, String invoiceNumber,
                                      String invoiceDate, Byte type, BigDecimal price, String checkCode)
            throws Exception {
        if (StringUtils.isBlank(invoiceCode)) {
            return BaseResponse.getError("发票代码不能为空");
        }

        if (StringUtils.isBlank(invoiceNumber)) {
            return BaseResponse.getError("发票号码不能为空");
        }

        if (StringUtils.isBlank(invoiceDate)) {
            return BaseResponse.getError("开票日期不能为空");
        }

        Date date = DateUtil.formatDate(invoiceDate, DateUtil.DATE_DEFAULT_FORMAT);
        if (date == null) {
            return BaseResponse.getError("开票日期格式不正确");
        }

        if (type == null) {
            return BaseResponse.getError("发票类型不能为空");
        }
        if (!Arrays.asList(Constant.DRUGINVOICE_TYPE_STOCK, Constant.DRUGINVOICE_TYPE_SALE).contains(type)) {
            return BaseResponse.getError("发票类型不合法");
        }

        if (StringUtils.isBlank(invoiceDate)) {
            return BaseResponse.getError("发票金额不能为空");
        }

        drugInvoiceService.create(store, invoiceCode, invoiceNumber, date, type, price, checkCode);
        return BaseResponse.getSuccess("");
    }

    /**
     * 药品进货同行单上传
     */
    @PostMapping("uploadDeliver")
    public BaseResponse uploadDeliver(@RequestAttribute DrugStore store, String deliverCode,
                                      String deliverDate, String price, String drugItems, Integer type, String idcard,
                                      String tradeName, String outNo)
            throws ServiceException {
        if (StringUtils.isBlank(deliverCode)) {
            return BaseResponse.getError("随货同行单号不能为空");
        }
        if (StringUtils.isBlank(deliverDate)) {
            return BaseResponse.getError("开票时间不能为空");
        }
        if (StringUtils.isBlank(price)) {
            return BaseResponse.getError("金额不能为空");
        }
        if (StringUtils.isBlank(drugItems)) {
            return BaseResponse.getError("清单明细不能为空");
        }
        if (!Arrays.asList(
                Constant.STOCK_IN_TYPE_PURCHASE,    // 采购入
                Constant.STOCK_IN_TYPE_TRANSFER_IN, // 调拨入
                Constant.STOCK_IN_TYPE_PROFIT,      // 盘盈
                Constant.STOCK_IN_TYPE_SELL_BACK    // 销售退回
        ).contains(type)) {
            return BaseResponse.getError("类型不合法");
        }
        Date deliverDateObj;
        try {
            deliverDateObj = FastDateFormat.getInstance("yyyy-MM-dd").parse(deliverDate);
        } catch (ParseException e) {
            return BaseResponse.getError("开票时间格式不正确");
        }

        List<DrugItemVO> items;
        try {
            items = JSON.parseArray(drugItems, DrugItemVO.class);
        } catch (Exception e) {
            return BaseResponse.getError("清单明细格式不正确");
        }
        // 判断价格不能为空
        boolean match = items.stream().anyMatch(s -> StringUtils.isBlank(s.getUnitPrice()));
        if (match) {
            throw new ServiceException("入库药品单价不能为空!");
        }

        for (DrugItemVO vo : items) {
            if (StringUtils.isBlank(vo.getPresentStock() + "")) {
                return BaseResponse.getError("当前库存信息不能为空");
            }
            if (Constant.STOCK_IN_TYPE_SELL_BACK.equals(type)){
                if (!StringUtils.isBlank(vo.getIdCard())) {
                    if (StringUtils.isBlank(tradeName)) {
                        return BaseResponse.getError("请输入医保卡姓名");
                    }
                    if (StringUtils.isBlank(outNo)) {
                        return BaseResponse.getError("请输入出库单号");
                    }
                }
                try{
                    new BigDecimal(vo.getUnitPrice());
                } catch (Exception ex){
                    throw new ServiceException("入库药品单价格式错误!");
                }
            }
        }
        drugDeliveryService.saveDelivery(store, deliverCode, deliverDateObj, price, type,
                                            items, idcard, tradeName, outNo);

        return BaseResponse.buildSuccess("成功");
    }

    /**
     * 发票和随货同行单关联
     */
    @PostMapping("relationInvoiceDeliver")
    public BaseResponse relationInvoiceDeliver(@RequestAttribute DrugStore store,
                                               String invoiceNo, String deliverCode) throws ServiceException {

        if (StringUtils.isBlank(invoiceNo)) {
            return BaseResponse.getError("发票号码不能为空");
        }

        if (StringUtils.isBlank(deliverCode)) {
            return BaseResponse.getError("随货单号不能为空");
        }

        drugDeliveryInvoiceRelationService.createRelation(store, invoiceNo, deliverCode);

        return BaseResponse.buildSuccess("成功");
    }

    /**
     * 药品出库上传
     */
    @PostMapping("uploadStoreOut")
    public BaseResponse uploadStoreOut(@RequestAttribute DrugStore store, String outNo, Integer type, Integer payType,
                                       String outDate, String outItems, String price, String insurancePrice,
                                       String tradeName, String idcard) throws ServiceException {

        if (StringUtils.isBlank(outNo)) {
            return BaseResponse.getError("出库单号不能为空");
        }
        if (StringUtils.isBlank(outDate)) {
            return BaseResponse.getError("出库开票时间不能为空");
        }
        if (type == null) {
            return BaseResponse.getError("类型不能为空");
        }
        if (type.equals(Constant.STOCK_OUT_TYPE_SELL) && payType == null) {
            return BaseResponse.getError("销售时付款方式不能为空");
        }
        if (StringUtils.isBlank(price)) {
            return BaseResponse.getError("金额不能为空");
        }
        if (!Arrays.asList(
                Constant.STOCK_OUT_TYPE_SELL,           // 销售出
                Constant.STOCK_OUT_TYPE_PURCHASE_BACK,  // 采购退货
                Constant.STOCK_OUT_TYPE_TRANSFER_OUT,   // 调拨出
                Constant.STOCK_OUT_TYPE_LOSS            // 盘亏
        ).contains(type)) {
            return BaseResponse.getError("类型不合法");
        }

        if (StringUtils.isBlank(outItems)) {
            return BaseResponse.getError("条目信息不能为空");
        }
        Date date;
        try {
            date = FastDateFormat.getInstance("yyyyMMddHHmmss").parse(outDate);
        } catch (ParseException e) {
            return BaseResponse.getError("出库开票时间格式不正确");
        }

        List<DrugItemVO> items;
        try {
            items = JSON.parseArray(outItems, DrugItemVO.class);
        } catch (Exception e) {
            return BaseResponse.getError("条目信息格式不正确");
        }
        for (DrugItemVO vo : items) {
            if (StringUtils.isBlank(vo.getPresentStock() + "")) {
                return BaseResponse.getError("当前库存信息不能为空");
            }
            if (!Arrays.asList(
                    Constant.INSURANCE_FLAG_TRUE,
                    Constant.INSURANCE_FLAG_FALSE
            ).contains(vo.getInsuranceFlag())) {
                return BaseResponse.getError("请输入正确的是否医保格式");
            }

            try {
                new BigDecimal(vo.getUnitPrice());
            } catch (Exception ex) {
                return BaseResponse.getError("出库药品单价格式错误");
            }
        }
        if (Constant.STOCK_OUT_TYPE_SELL.equals(type) &&
                ("".equals(payType) || null == payType)) {
            return BaseResponse.getError("支付方式不能为空");
        }
        if (!Constant.STOCK_OUT_TYPE_LOSS.equals(type) &&
                StringUtils.isBlank(tradeName)) {
            return BaseResponse.getError("交易方名称不能为空");
        }
        if (Constant.PAY_INSURANCE_TYPE.equals(payType)){
            if (StringUtils.isBlank(idcard)) {
                return BaseResponse.getError("医保卡身份证不能为空");
            }
            if (StringUtils.isBlank(insurancePrice)) {
                return BaseResponse.getError("请输入医保总额");
            }
        }
        drugStockOutService.saveStockOut(store, outNo, price, payType, type, date, items, insurancePrice,
                                         tradeName, idcard);
        return BaseResponse.buildSuccess("成功");
    }

    /**
     * 发票和随货同行单图片上传接口
     */
    @PostMapping("uploadFile")
    public BaseResponse uploadFile(@RequestAttribute DrugStore store, Byte type,
                                   String code, MultipartFile file) throws Exception {
        if (type == null) {
            return BaseResponse.getError("类型不能为空");
        }

        if (!Arrays.asList(Constant.DRUGIMAGE_TYPE_INVOICE, Constant.DRUGIMAGE_TYPE_DELIVERY).contains(type)) {
            return BaseResponse.getError("类型不合法");
        }

        if (StringUtils.isBlank(code)) {
            return BaseResponse.getError("编号不能为空");
        }


        if (file == null) {
            return BaseResponse.getError("图片文件不能为空");
        }

        if (file.getSize() > 1024 * 800) {
            return BaseResponse.getError("图片文件不能超过800k");
        }

        String path = fileService.uploadImg(file);
        DrugUploadImage drugUploadImage = drugUploadImageService.create(store, code, type, path);
        return BaseResponse.getSuccess(new HashMap<String, Object>() {{
            put("picCode", drugUploadImage.getImageCode());
        }});
    }

    @Autowired
    public void setDrugCatalogService(DrugCatalogService drugCatalogService) {
        this.drugCatalogService = drugCatalogService;
    }

    @Autowired
    public void setDrugInvoiceService(DrugInvoiceService drugInvoiceService) {
        this.drugInvoiceService = drugInvoiceService;
    }

    @Autowired
    public void setDrugDeliveryInvoiceRelationService(DrugDeliveryInvoiceRelationService
                                                              drugDeliveryInvoiceRelationService) {
        this.drugDeliveryInvoiceRelationService = drugDeliveryInvoiceRelationService;
    }

    @Autowired
    public void setDrugDeliveryService(DrugDeliveryService drugDeliveryService) {
        this.drugDeliveryService = drugDeliveryService;
    }

    @Autowired
    public void setDrugStockOutService(DrugStockOutService drugStockOutService) {
        this.drugStockOutService = drugStockOutService;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setDrugUploadImageService(DrugUploadImageService drugUploadImageService) {
        this.drugUploadImageService = drugUploadImageService;
    }
}
