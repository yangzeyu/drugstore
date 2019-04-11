package com.jsmscp.dr.dto;

import java.math.BigDecimal;

public class UploadPriceDto {

    private BigDecimal deliveryPrice;

    private BigDecimal stockOutPrice;

    private BigDecimal insurancePrice;


    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public BigDecimal getStockOutPrice() {
        return stockOutPrice;
    }

    public void setStockOutPrice(BigDecimal stockOutPrice) {
        this.stockOutPrice = stockOutPrice;
    }

    public BigDecimal getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(BigDecimal insurancePrice) {
        this.insurancePrice = insurancePrice;
    }
}
