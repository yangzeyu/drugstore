package com.jsmscp.dr.entity;

import java.util.Date;

public class MedicalInsurance {
    private Long id;

    private String medicalInsuranceCode;

    private String threeDirectoryName;

    private String threeDirectoryType;

    private Byte collectType;

    private String collectLvl;

    private String dosageForm;

    private Byte status;

    private String spec;

    private String ext;

    private String ext1;

    private String ext2;

    private Date createAt;

    private Date updateAt;

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

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
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
}
