package com.jsmscp.dr.dto;

public class OperateDto {

    private Integer id;

    private String appCode;

    private String url;

    private String storeName;

    private String respContent;

    private String operateAt;

    private String reqParam;

    public String getReqParam() {
        return reqParam;
    }

    public void setReqParam(String reqParam) {
        this.reqParam = reqParam;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getRespContent() {
        return respContent;
    }

    public void setRespContent(String respContent) {
        this.respContent = respContent;
    }

    public String getOperateAt() {
        return operateAt;
    }

    public void setOperateAt(String operateAt) {
        this.operateAt = operateAt;
    }
}
