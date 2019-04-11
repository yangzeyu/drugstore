package com.jsmscp.dr.dto;

public class CompareMedicalInsuranceDto {

    private Long drugId;

    private String medicalInsuranceCode;

    private Long insuranceId;

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public String getMedicalInsuranceCode() {
        return medicalInsuranceCode;
    }

    public void setMedicalInsuranceCode(String medicalInsuranceCode) {
        this.medicalInsuranceCode = medicalInsuranceCode;
    }

    public Long getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Long insuranceId) {
        this.insuranceId = insuranceId;
    }
}
