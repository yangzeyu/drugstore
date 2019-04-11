package com.jsmscp.dr.service.impl.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.InvoiceCompareDto;
import com.jsmscp.dr.dto.InvoiceDrugDto;
import com.jsmscp.dr.dto.QueryInvoiceDto;
import com.jsmscp.dr.dto.StoreDeliveryInvoiceReportDto;
import com.jsmscp.dr.entity.DrugDeliveryItem;
import com.jsmscp.dr.entity.DrugInvoice;
import com.jsmscp.dr.entity.DrugUploadImage;
import com.jsmscp.dr.mapper.DrugDeliveryItemMapper;
import com.jsmscp.dr.mapper.DrugInvoiceMapper;
import com.jsmscp.dr.mapper.DrugUploadImageMapper;
import com.jsmscp.dr.service.impl.exception.ServiceException;
import com.jsmscp.dr.service.report.InvoiceCompareService;
import com.jsmscp.dr.util.HttpUtils;
import com.jsmscp.dr.util.JsonUtils;
import com.jsmscp.dr.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class InvoiceCompareServiceImpl implements InvoiceCompareService {

    private DrugInvoiceMapper drugInvoiceMapper;

    private DrugUploadImageMapper drugUploadImageMapper;

    @Autowired
    public InvoiceCompareServiceImpl(DrugInvoiceMapper drugInvoiceMapper,
                                     DrugUploadImageMapper drugUploadImageMapper) {
        this.drugInvoiceMapper = drugInvoiceMapper;
        this.drugUploadImageMapper = drugUploadImageMapper;
    }




    @Override
    public HashMap<String, Object> findInvoiceReport(Integer storeId, String invoiceCode, Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        Integer pageSize = Constant.DEFAULT_PAGE_SIZE;
        map.put("pageSize", pageSize);
        map.put("pageNo", pageNo);
        pageNo = (pageNo - 1) * pageSize;
        List<InvoiceCompareDto> list = drugInvoiceMapper.findInvoiceReport(storeId, invoiceCode, pageNo, pageSize);

        List<Long> itemList = drugUploadImageMapper.findAllItemId();
        for (InvoiceCompareDto icd : list) {
            boolean hasImage = itemList.contains(icd.getItemId());
            if (hasImage) {
                icd.setIsUploadImage(Constant.UPLOADIMAGE_TRUE);
            } else {
                icd.setIsUploadImage(Constant.UPLOADIMAGE_FALSE);
            }
        }

        if (!(StringUtils.isBlank(String.valueOf(storeId))) && !(StringUtils.isBlank(invoiceCode))) { //判断是否查询单个发票
            DrugInvoice drugInvoice = drugInvoiceMapper.findInvoice(storeId, invoiceCode);
            if (null == drugInvoice) {
                throw new ServiceException("未查询到该发票!");
            }
            if (!Constant.QUERY_INVOICE_STATUS_TRUE.equals(drugInvoice.getQueryInvoiceStatus())) { //查询发票返回状态
                String message = getQueryInvoice(drugInvoice);
                try {
                    QueryInvoiceDto invoiceMsg = JsonUtils.parseObject(message, QueryInvoiceDto.class);
                    if (Constant.QUERY_SUCCESS.equals(invoiceMsg.getSuccess())) {
                        map.put("invoice", invoiceMsg);
                    }else{
                        drugInvoice.setQueryInvoiceStatus(Constant.QUERY_INVOICE_STATUS_FALSE);
                        throw new ServiceException("发票信息错误!");
                    }
                } catch (Exception e) {
                    drugInvoice.setQueryInvoiceStatus(Constant.QUERY_INVOICE_STATUS_FALSE);
                    drugInvoice.setReqStatus(Constant.QUERY_INVOICE_STATUS_FALSE);
                    throw new ServiceException("发票查询失败,请稍后重试!");
                }
            } else {
                List<QueryInvoiceDto> invoiceMsg = JSON.parseArray(drugInvoice.getReturnMessage(),
                                                                    QueryInvoiceDto.class);
                List<InvoiceCompareDto> invoiceList = drugInvoiceMapper.findInvoiceMsg(storeId, invoiceCode);
                map.put("invoice", invoiceMsg);
                map.put("invoiceList", invoiceList);
            }
        }
        int count = drugInvoiceMapper.findInvoiceReportCount(storeId, invoiceCode);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        map.put("list", list);
        return map;
    }

    @Override
    public HashMap<String, Object> findInvoiceDeliverReport(Integer storeId, String invoiceCode,
                                                            Byte invoiceType, Integer pageNo) {

        HashMap<String, Object> map = new HashMap<>();
        Integer pageSize = Constant.DEFAULT_PAGE_SIZE;
        map.put("pageSize", pageSize);
        map.put("pageNo", pageNo);
        pageNo = (pageNo - 1) * pageSize;
        List<StoreDeliveryInvoiceReportDto> list = drugInvoiceMapper.findInvoiceDeliverReport(storeId, invoiceCode,
                invoiceType, pageNo, pageSize);

        for (StoreDeliveryInvoiceReportDto sdir: list) {
            if (sdir.getInvoicePrice().equals(sdir.getDeliverPrice())) {
                sdir.setIsCorrect(Constant.PRICE_CORRECT_TRUE);
            } else {
                sdir.setIsCorrect(Constant.PRICE_CORRECT_FALSE);
            }
        }
        int count = drugInvoiceMapper.findInvoiceDeliverCount(storeId, invoiceCode, invoiceType);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        map.put("list", list);
        return map;
    }

    public String getQueryInvoice (DrugInvoice drugInvoice){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String url;
        url = Constant.QUERY_INVOICE_URL + "fpdm=" + drugInvoice.getInvoiceNumber()+
                "&fphm=" + drugInvoice.getInvoiceCode() +
                "&kprq=" + dateFormat.format(drugInvoice.getInvoiceDate()) +
                "&checkCode=" + drugInvoice.getCheckCode() +
                "&noTaxAmount=" + drugInvoice.getPrice();
        String returnMessage = HttpUtils.queryInvoice(url, Constant.QUERY_INVOICE_HEADER);
        return returnMessage;
    }
}
