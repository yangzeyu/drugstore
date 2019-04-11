package com.jsmscp.dr.api.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class DrugVO implements Serializable {

    private static final long serialVersionUID = -8641257692408720241L;

    private String platformCode;
    private String drugName; //药品名称
    private String dosageForm; //剂型
    private String spec; //规格
    private String unit; //包装单位
    private String standSpecRate; //转换系数
    private String manufacturer; //生产厂家
    private String direction; //给药途径
    private String freq; //用法
    private String usingNumber; //每次用量
    private String usingUnit; //用量单位
    private String maxOnceNumber; //单次最大开方
    private BigDecimal presentStock; //药店库存


    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStandSpecRate() {
        return standSpecRate;
    }

    public void setStandSpecRate(String standSpecRate) {
        this.standSpecRate = standSpecRate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getUsingNumber() {
        return usingNumber;
    }

    public void setUsingNumber(String usingNumber) {
        this.usingNumber = usingNumber;
    }

    public String getUsingUnit() {
        return usingUnit;
    }

    public void setUsingUnit(String usingUnit) {
        this.usingUnit = usingUnit;
    }

    public String getMaxOnceNumber() {
        return maxOnceNumber;
    }

    public void setMaxOnceNumber(String maxOnceNumber) {
        this.maxOnceNumber = maxOnceNumber;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public BigDecimal getPresentStock() {
        return presentStock;
    }

    public void setPresentStock(BigDecimal presentStock) {
        this.presentStock = presentStock;
    }
}
