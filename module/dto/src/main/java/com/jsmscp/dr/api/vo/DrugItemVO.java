package com.jsmscp.dr.api.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class DrugItemVO implements Serializable {

    private static final long serialVersionUID = -4709604953461791962L;

    private String drugCode;
    private String name;
    private BigDecimal quantity;
    private String spec;
    private String dosageForm;
    private String unit;
    private String batchNo;
    private String unitPrice;
    private String expire;
    private String mfr;
    private BigDecimal presentStock;
    private Byte insuranceFlag;
    private String idCard;
    private String stardName;
    private String outNo;


    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getMfr() {
        return mfr;
    }

    public void setMfr(String mfr) {
        this.mfr = mfr;
    }

    public BigDecimal getPresentStock() {
        return presentStock;
    }

    public void setPresentStock(BigDecimal presentStock) {
        this.presentStock = presentStock;
    }

    public Byte getInsuranceFlag() {
        return insuranceFlag;
    }

    public void setInsuranceFlag(Byte insuranceFlag) {
        this.insuranceFlag = insuranceFlag;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getStardName() {
        return stardName;
    }

    public void setStardName(String stardName) {
        this.stardName = stardName;
    }

    public String getOutNo() {
        return outNo;
    }

    public void setOutNo(String outNo) {
        this.outNo = outNo;
    }
}
