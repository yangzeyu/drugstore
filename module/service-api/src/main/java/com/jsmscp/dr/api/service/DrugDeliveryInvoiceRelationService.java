package com.jsmscp.dr.api.service;

import com.jsmscp.dr.api.service.exception.ServiceException;
import com.jsmscp.dr.entity.DrugDelivery;
import com.jsmscp.dr.entity.DrugDeliveryInvoiceRelation;
import com.jsmscp.dr.entity.DrugInvoice;
import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.mapper.DrugDeliveryInvoiceRelationMapper;
import com.jsmscp.dr.mapper.DrugDeliveryMapper;
import com.jsmscp.dr.mapper.DrugInvoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DrugDeliveryInvoiceRelationService {

    private DrugDeliveryMapper drugDeliveryMapper;

    private DrugInvoiceMapper drugInvoiceMapper;

    private DrugDeliveryInvoiceRelationMapper drugDeliveryInvoiceRelationMapper;

    public void createRelation(DrugStore store, String invoiceNo, String deliverCode) throws ServiceException {

        DrugDelivery delivery = drugDeliveryMapper.findByCode(deliverCode, store.getId().longValue());
        if (delivery == null) {
            throw new ServiceException("随货单号不存在");
        }
        DrugInvoice invoice = drugInvoiceMapper.findByNumber(invoiceNo, store.getId());
        if (invoice == null) {
            throw new ServiceException("发票号码不存在");
        }
        createRelation(invoice.getId(), delivery.getId());
    }

    private DrugDeliveryInvoiceRelation createRelation(Long invoiceId, Long deliveryId) {
        DrugDeliveryInvoiceRelation relation = new DrugDeliveryInvoiceRelation();
        relation.setInvoiceId(invoiceId);
        relation.setDeliveryId(deliveryId);
        relation.setCreateAt(new Date());
        relation.setUpdateAt(new Date());
        drugDeliveryInvoiceRelationMapper.insert(relation);
        return relation;
    }

    @Autowired
    public void setDrugDeliveryInvoiceRelationMapper(DrugDeliveryInvoiceRelationMapper
                                                             drugDeliveryInvoiceRelationMapper) {
        this.drugDeliveryInvoiceRelationMapper = drugDeliveryInvoiceRelationMapper;
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
