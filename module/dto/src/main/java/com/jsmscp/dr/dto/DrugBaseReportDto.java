package com.jsmscp.dr.dto;

import java.math.BigDecimal;

public class DrugBaseReportDto {

    private String goodName;

    private String dosageForm;

    private String spec;

    private String manufactureName;

    private String drugStoreName;

    private BigDecimal storeInPrice;

    private BigDecimal storeInNumber;

    private BigDecimal storeOutPrice;

    private BigDecimal storeOutNumber;

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

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getManufactureName() {
        return manufactureName;
    }

    public void setManufactureName(String manufactureName) {
        this.manufactureName = manufactureName;
    }

    public String getDrugStoreName() {
        return drugStoreName;
    }

    public void setDrugStoreName(String drugStoreName) {
        this.drugStoreName = drugStoreName;
    }

    public BigDecimal getStoreInPrice() {
        return storeInPrice;
    }

    public void setStoreInPrice(BigDecimal storeInPrice) {
        this.storeInPrice = storeInPrice;
    }

    public BigDecimal getStoreInNumber() {
        return storeInNumber;
    }

    public void setStoreInNumber(BigDecimal storeInNumber) {
        this.storeInNumber = storeInNumber;
    }

    public BigDecimal getStoreOutPrice() {
        return storeOutPrice;
    }

    public void setStoreOutPrice(BigDecimal storeOutPrice) {
        this.storeOutPrice = storeOutPrice;
    }

    public BigDecimal getStoreOutNumber() {
        return storeOutNumber;
    }

    public void setStoreOutNumber(BigDecimal storeOutNumber) {
        this.storeOutNumber = storeOutNumber;
    }
}
