package com.jsmscp.dr.dto;

import java.math.BigDecimal;

public class DrugStockOutDto {


    private Integer id;

    private String storeName;

    private Integer storeId;

    private String outNo;

    private String outDate;

    private Long drugCatalogId;

    private String isPairCode;

    private Byte payType;

    private Byte type;

    private BigDecimal price;

    private BigDecimal insurancePrice;

    private BigDecimal quantity;

    private String tradeName;

    private String idcard;


    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getIsPairCode() {
        return isPairCode;
    }

    public void setIsPairCode(String isPairCode) {
        this.isPairCode = isPairCode;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getOutNo() {
        return outNo;
    }

    public void setOutNo(String outNo) {
        this.outNo = outNo;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public Long getDrugCatalogId() {
        return drugCatalogId;
    }

    public void setDrugCatalogId(Long drugCatalogId) {
        this.drugCatalogId = drugCatalogId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(BigDecimal insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
