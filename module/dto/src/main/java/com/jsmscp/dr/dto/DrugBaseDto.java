package com.jsmscp.dr.dto;


import java.math.BigDecimal;

public class DrugBaseDto {

    private Long id;

    private String name;

    private String goodName;

    private String dosageForm;

    private String platformCode;

    private String type;

    private String spec;

    private String unit;

    private Byte status;

    private Integer standSpecRate;

    private Long insuranceId;

    private BigDecimal unitPrice;

    private BigDecimal retailPrice;

    private String permissionNumber;

    private String manufactureName;

    private Long medicalInsuranceId;

    private Integer manufactureId;

    private String onceNumber;

    private String onceUnit;

    private String freqCode;

    private String freqName;

    private String direction;

    private String medicalInsuranceCode;

    private String collectLvl;

    private Byte collectType;

    private String threeDirectorytype;

    public String getCollectLvl() {
        return collectLvl;
    }

    public void setCollectLvl(String collectLvl) {
        this.collectLvl = collectLvl;
    }

    public Byte getCollectType() {
        return collectType;
    }

    public void setCollectType(Byte collectType) {
        this.collectType = collectType;
    }

    public String getThreeDirectorytype() {
        return threeDirectorytype;
    }

    public void setThreeDirectorytype(String threeDirectorytype) {
        this.threeDirectorytype = threeDirectorytype;
    }

    public Long getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }

    public Integer getManufactureId() {
        return manufactureId;
    }

    public void setManufactureId(Integer manufactureId) {
        this.manufactureId = manufactureId;
    }

    public Long getMedicalInsuranceId() {
        return medicalInsuranceId;
    }

    public void setMedicalInsuranceId(Long medicalInsuranceId) {
        this.medicalInsuranceId = medicalInsuranceId;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

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

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getMedicalInsuranceCode() {
        return medicalInsuranceCode;
    }

    public void setMedicalInsuranceCode(String medicalInsuranceCode) {
        this.medicalInsuranceCode = medicalInsuranceCode;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getManufactureName() {
        return manufactureName;
    }

    public void setManufactureName(String manufactureName) {
        this.manufactureName = manufactureName;
    }
}
