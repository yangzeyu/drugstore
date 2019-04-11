package com.jsmscp.dr.dto;

import java.util.Date;

public class MedicalInsuranceDto {

    private Long id;

    private String medicalInsuranceCode;

    private String threeDirectoryName;

    private String threeDirectoryType;

    private Byte collectType;

    private String collectLvl;

    private String dosageForm;

    private Byte status;

    private String spec;

    private Date createAt;

    private Date updateAt;

    private Byte drugStatus;

    private Long drugId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getThreeDirectoryType() {
        return threeDirectoryType;
    }

    public void setThreeDirectoryType(String threeDirectoryType) {
        this.threeDirectoryType = threeDirectoryType;
    }

    public Byte getCollectType() {
        return collectType;
    }

    public void setCollectType(Byte collectType) {
        this.collectType = collectType;
    }

    public String getCollectLvl() {
        return collectLvl;
    }

    public void setCollectLvl(String collectLvl) {
        this.collectLvl = collectLvl;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
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

    public Byte getDrugStatus() {
        return drugStatus;
    }

    public void setDrugStatus(Byte drugStatus) {
        this.drugStatus = drugStatus;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }
}
