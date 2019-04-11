package com.jsmscp.dr.dto;

import java.math.BigDecimal;

public class StoreOutInReportDto {

    private Long catalogId;

    private String storeName;

    private String goodName;

    private String spec;

    private String dosageForm;

    private String  manufacture;

    private BigDecimal inQuantity;

    private BigDecimal outQuantity;

    private BigDecimal stock;

    private BigDecimal initStock;

    private BigDecimal storeStock;

    public BigDecimal getInitStock() {
        return initStock;
    }

    public void setInitStock(BigDecimal initStock) {
        this.initStock = initStock;
    }

    public BigDecimal getStoreStock() {
        return storeStock;
    }

    public void setStoreStock(BigDecimal storeStock) {
        this.storeStock = storeStock;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
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

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public BigDecimal getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(BigDecimal inQuantity) {
        this.inQuantity = inQuantity;
    }

    public BigDecimal getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(BigDecimal outQuantity) {
        this.outQuantity = outQuantity;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }
}
