package com.jsmscp.dr.api.vo;

import java.math.BigDecimal;

public class DrugInfoVO extends DrugVO {

    private static final long serialVersionUID = 2349069833243618132L;

    private Integer status;
    private Long insuranceId;
    private BigDecimal stock;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }
}
