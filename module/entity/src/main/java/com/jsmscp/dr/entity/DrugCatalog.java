package com.jsmscp.dr.entity;


import java.math.BigDecimal;
import java.util.Date;

public class DrugCatalog {
    private Long id;

    private String drugCode;

    private Long drugId;

    private Integer storeId;

    private String unit;

    private String goodName;

    private String manufacture;

    private Byte status;

    private String standSpecRate;

    private String dosageForm;

    private Integer conversionRatio;

    private Byte isEmergent;

    private String spec;

    private String onceNumber;

    private Long insuranceId;

    private String onceUnit;

    private String freqCode;

    private String freqName;

    private Integer maxOnceNumber;

    private String direction;

    private Integer lowerLimit;

    private Integer upperLimit;

    private BigDecimal initStock;

    private BigDecimal storeStock;

    private BigDecimal stock;

    private Date createAt;

    private Date updateAt;

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

    public String getStandSpecRate() {
        return standSpecRate;
    }

    public void setStandSpecRate(String standSpecRate) {
        this.standSpecRate = standSpecRate;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public Integer getConversionRatio() {
        return conversionRatio;
    }

    public void setConversionRatio(Integer conversionRatio) {
        this.conversionRatio = conversionRatio;
    }

    public Byte getIsEmergent() {
        return isEmergent;
    }

    public void setIsEmergent(Byte isEmergent) {
        this.isEmergent = isEmergent;
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

    public Long getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
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

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
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

    private DrugBase drugBase;

    public DrugBase getDrugBase() {
        return drugBase;
    }

    public void setDrugBase(DrugBase drugBase) {
        this.drugBase = drugBase;
    }

    public static final byte STATUS_NORMAL = 1;
    public static final byte STATUS_ABNORMAL = 0;
}
