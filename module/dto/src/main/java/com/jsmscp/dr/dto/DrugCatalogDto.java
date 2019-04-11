package com.jsmscp.dr.dto;


import java.math.BigDecimal;

public class DrugCatalogDto {

    private Long id;

    private String drugCode;

    private Long drugId;


    private Integer storeId;

    private String unit;

    private String storeName;

    private String drugName;

    private String goodName;

    private String manufacture;

    private Byte status;

    private Long insuranceId;

    private String standSpecRate;

    private Integer conversionRatio;

    private String dosageForm;

    private String spec;

    private String onceNumber;

    private String onceUnit;

    private String freqCode;

    private String freqName;

    private Integer maxOnceNumber;

    private String direction;

    private Integer lowerLimit;

    private Integer upperLimit;

    private BigDecimal stock;

    private BigDecimal initStock;

    private BigDecimal storeStock;

    public BigDecimal getStoreStock() {
        return storeStock;
    }

    public void setStoreStock(BigDecimal storeStock) {
        this.storeStock = storeStock;
    }

    public BigDecimal getInitStock() {
        return initStock;
    }

    public void setInitStock(BigDecimal initStock) {
        this.initStock = initStock;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }



    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getStandSpecRate() {
        return standSpecRate;
    }

    public void setStandSpecRate(String standSpecRate) {
        this.standSpecRate = standSpecRate;
    }

    public Integer getConversionRatio() {
        return conversionRatio;
    }

    public void setConversionRatio(Integer conversionRatio) {
        this.conversionRatio = conversionRatio;
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

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Integer lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Integer upperLimit) {
        this.upperLimit = upperLimit;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

}
