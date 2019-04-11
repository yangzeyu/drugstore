package com.jsmscp.dr.api.service;

import com.jsmscp.dr.api.service.exception.ServiceException;
import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.entity.DrugDelivery;
import com.jsmscp.dr.entity.DrugInvoice;
import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.entity.DrugUploadImage;
import com.jsmscp.dr.mapper.DrugDeliveryMapper;
import com.jsmscp.dr.mapper.DrugInvoiceMapper;
import com.jsmscp.dr.mapper.DrugUploadImageMapper;
import com.jsmscp.dr.util.GenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class DrugUploadImageService {

    private DrugUploadImageMapper drugUploadImageMapper;
    private DrugDeliveryMapper drugDeliveryMapper;
    private DrugInvoiceMapper drugInvoiceMapper;

    @Transactional
    public DrugUploadImage create(DrugStore drugStore, String code, Byte type, String addr) throws Exception {
        DrugUploadImage drugUploadImage = new DrugUploadImage();
        Long itemId = null;
        switch (type) {
            case Constant.DRUGIMAGE_TYPE_INVOICE:
                DrugInvoice drugInvoice = drugInvoiceMapper.findByNumber(code, drugStore.getId());
                if (drugInvoice != null) {
                    itemId = drugInvoice.getId();
                }
                break;

            case Constant.DRUGIMAGE_TYPE_DELIVERY:
                DrugDelivery drugDelivery = drugDeliveryMapper.findByCode(code, (long) drugStore.getId());
                if (drugDelivery != null) {
                    itemId = drugDelivery.getId();
                }
                break;
        }

        if (itemId == null) {
            throw new ServiceException("编号不存在");
        }
        drugUploadImage.setItemId(itemId);
        drugUploadImage.setType(type);
        drugUploadImage.setImageAddr(addr);
        drugUploadImage.setImageCode(GenUtil.genImageCode());
        drugUploadImage.setCreateAt(new Date());
        drugUploadImageMapper.insert(drugUploadImage);
        return drugUploadImage;
    }


    @Autowired
    public void setDrugUploadImageMapper(DrugUploadImageMapper drugUploadImageMapper) {
        this.drugUploadImageMapper = drugUploadImageMapper;
    }

    @Autowired
    public void setDrugDeliveryMapper(DrugDeliveryMapper drugDeliveryMapper) {
        this.drugDeliveryMapper = drugDeliveryMapper;
    }

    @Autowired
    public void setDrugInvoiceMapper(DrugInvoiceMapper drugInvoiceMapper) {
        this.drugInvoiceMapper = drugInvoiceMapper;
    }
}
