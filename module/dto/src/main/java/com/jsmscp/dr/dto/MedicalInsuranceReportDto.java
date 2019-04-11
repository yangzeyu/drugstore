package com.jsmscp.dr.dto;

import java.math.BigDecimal;

public class MedicalInsuranceReportDto {

    private String medicalInsuranceCode;

    private String threeDirectoryName;

    private String storeName;

    private String platformCode;

    private String goodName;

    private String dosageForm;

    private String manufactureName;

    private String spec;

    private BigDecimal storeOutNumber;

    private String collectLvl;

    public String getMedicalInsuranceCode() {
        return medicalInsuranceCode;
    }

    public void setMedicalInsuranceCode(String medicalInsuranceCode) {
        this.medicalInsuranceCode = medicalInsuranceCode;
    }

    public String getThreeDirectoryName() {
        return threeDirectoryName;
    }

    public void setThreeDirectoryName(String threeDirectoryName) {
        this.threeDirectoryName = threeDirectoryName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getManufactureName() {
        return manufactureName;
    }

    public void setManufactureName(String manufactureName) {
        this.manufactureName = manufactureName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public BigDecimal getStoreOutNumber() {
        return storeOutNumber;
    }

    public void setStoreOutNumber(BigDecimal storeOutNumber) {
        this.storeOutNumber = storeOutNumber;
    }

    public String getCollectLvl() {
        return collectLvl;
    }

    public void setCollectLvl(String collectLvl) {
        this.collectLvl = collectLvl;
    }
}
