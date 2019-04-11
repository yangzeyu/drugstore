package com.jsmscp.dr.dto;

public class CompareDrugCodeDto {

    private Integer storeId;

    private String storeName;

    private Integer drugNumber;

    private Integer emergentNumber;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getDrugNumber() {
        return drugNumber;
    }

    public void setDrugNumber(Integer drugNumber) {
        this.drugNumber = drugNumber;
    }

    public Integer getEmergentNumber() {
        return emergentNumber;
    }

    public void setEmergentNumber(Integer emergentNumber) {
        this.emergentNumber = emergentNumber;
    }
}
