package com.jsmscp.dr.service.impl;

import com.jsmscp.dr.entity.DrugInvoice;
import com.jsmscp.dr.entity.DrugUploadImage;
import com.jsmscp.dr.mapper.DrugUploadImageMapper;
import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.dto.DeliverInvoiceDto;
import com.jsmscp.dr.mapper.DrugDeliveryInvoiceRelationMapper;
import com.jsmscp.dr.service.DeliverInvoiceService;

import java.util.HashMap;
import java.util.List;

@Service
public class DeliverInvoiceServiceImpl implements DeliverInvoiceService {
    private DrugDeliveryInvoiceRelationMapper drugDeliveryInvoiceRelationMapper;

    private DrugUploadImageMapper drugUploadImageMapper;

    @Autowired
    public DeliverInvoiceServiceImpl(DrugDeliveryInvoiceRelationMapper drugDeliveryInvoiceRelationMapper,
                                     DrugUploadImageMapper drugUploadImageMapper) {
        this.drugDeliveryInvoiceRelationMapper = drugDeliveryInvoiceRelationMapper;
        this.drugUploadImageMapper = drugUploadImageMapper;
    }




    /**
     * 查询随货同行单和发票信息
     * @param storeId
     * @param keyword
     * @param startTime
     * @param endTime
     * @param pageNo
     * @return
     */
    @Override
    public HashMap<String, Object> list(Integer storeId, String keyword, String startTime, String endTime,
                                        Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNo);
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        List<DeliverInvoiceDto> list = drugDeliveryInvoiceRelationMapper.findDeliverInvoice(storeId, keyword,
                startTime, endTime, pageNo, pageSize);
        int count = drugDeliveryInvoiceRelationMapper.findCount(storeId, keyword, startTime, endTime);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        map.put("list", list);
        map.put("pageSize", pageSize);
        return map;
    }


    /**
     * 查询发票和随货单图片路径
     * @param deliveryId
     * @return
     */
    @Override
    public HashMap<String, Object> findInvoiceImg(Integer deliveryId, String imagePrefix) {
        HashMap<String, Object> map = new HashMap();
        List<String> deliverList = drugUploadImageMapper.findInvoiceImg(deliveryId);
        List<String> invoiceList = drugUploadImageMapper.findDeliverImg(deliveryId);
        map.put("deliverList", refill(deliverList, imagePrefix));
        map.put("invoiceList", refill(invoiceList, imagePrefix));
        return map;
    }

    private List<String> refill(List<String> list, String prefix) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, prefix + list.get(i));
        }
        return list;
    }
}
