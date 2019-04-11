package com.jsmscp.dr.api.service;

import com.jsmscp.dr.api.service.exception.ServiceException;
import com.jsmscp.dr.entity.DrugInvoice;
import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.mapper.DrugInvoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class DrugInvoiceService {

    private DrugInvoiceMapper drugInvoiceMapper;



    @Transactional
    public DrugInvoice create(DrugStore store, String invoiceCode, String invoiceNumber,
                       Date invoiceDate, Byte type, BigDecimal price, String checkCode) throws Exception {
        DrugInvoice drugInvoice = drugInvoiceMapper.findByNumber(invoiceNumber, store.getId());
        if (drugInvoice != null) {
            throw new ServiceException("发票号码已存在");
        }
        drugInvoice = new DrugInvoice();
        drugInvoice.setStoreId(store.getId());
        drugInvoice.setInvoiceCode(invoiceCode);
        drugInvoice.setInvoiceNumber(invoiceNumber);
        drugInvoice.setInvoiceDate(invoiceDate);
        drugInvoice.setType(type);
        drugInvoice.setPrice(price.toString());
        drugInvoice.setCheckCode(checkCode);
        drugInvoice.setCreateAt(new Date());
        drugInvoiceMapper.insert(drugInvoice);
        return drugInvoice;
    }

    @Autowired
    public void setDrugInvoiceMapper(DrugInvoiceMapper drugInvoiceMapper) {
        this.drugInvoiceMapper = drugInvoiceMapper;
    }
}
