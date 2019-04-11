package com.jsmscp.dr.entity;

import java.math.BigDecimal;
import java.util.Date;

public class DrugBase {
    private Long id;

    private String name;

    private Long insuranceId;

    private String platformCode;

    private String namePinyin;

    private String goodName;

    private String goodNamePinyin;

    private String dosageForm;

    private Byte type;

    private String spec;

    private Byte status;

    private String unit;

    private Integer standSpecRate;

    private BigDecimal unitPrice;

    private BigDecimal retailPrice;

    private String permissionNumber;

    private Integer manufactureId;

    private Byte isMedicalInsurance;

    private String onceNumber;

    private String onceUnit;

    private String freqCode;

    private String freqName;

    private Integer maxOnceNumber;

    private String direction;

    private Date createAt;

    private Date updateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodNamePinyin() {
        return goodNamePinyin;
    }

    public void setGoodNamePinyin(String goodNamePinyin) {
        this.goodNamePinyin = goodNamePinyin;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getStandSpecRate() {
        return standSpecRate;
    }

    public void setStandSpecRate(Integer standSpecRate) {
        this.standSpecRate = standSpecRate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getPermissionNumber() {
        return permissionNumber;
    }

    public void setPermissionNumber(String permissionNumber) {
        this.permissionNumber = permissionNumber;
    }

    public Integer getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(Integer manufactureId) {
        this.manufactureId = manufactureId;
    }

    public Byte getIsMedicalInsurance() {
        return isMedicalInsurance;
    }

    public void setIsMedicalInsurance(Byte isMedicalInsurance) {
        this.isMedicalInsurance = isMedicalInsurance;
    }

    public String getOnceNumber() {
        return onceNumber;
    }

    public void setOnceNumber(String onceNumber) {
        this.onceNumber = onceNumber;
    }

    public String getOnceUnit() {
        return onceUnit;
    }

    public void setOnceUnit(String onceUnit) {
        this.onceUnit = onceUnit;
    }

    public String getFreqCode() {
        return freqCode;
    }

    public void setFreqCode(String freqCode) {
        this.freqCode = freqCode;
    }

    public String getFreqName() {
        return freqName;
    }

    public void setFreqName(String freqName) {
        this.freqName = freqName;
    }

    public Integer getMaxOnceNumber() {
        return maxOnceNumber;
    }

    public void setMaxOnceNumber(Integer maxOnceNumber) {
        this.maxOnceNumber = maxOnceNumber;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}

