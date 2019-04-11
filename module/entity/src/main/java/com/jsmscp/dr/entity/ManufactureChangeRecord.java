package com.jsmscp.dr.entity;

import java.util.Date;

public class ManufactureChangeRecord {
    private Integer id;

    private Integer agoId;

    private Integer newId;

    private Date createAt;

    private Date updateAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgoId() {
        return agoId;
    }

    public void setAgoId(Integer agoId) {
        this.agoId = agoId;
    }

    public Integer getNewId() {
        return newId;
    }

    public void setNewId(Integer newId) {
        this.newId = newId;
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
