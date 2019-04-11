package com.jsmscp.dr.api.common;

public class BaseResponse {
    public static final Integer SUCCESS_CODE = 1;
    public static final Integer ERROR_CODE = 0;

    private Integer code;
    private String msg;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static BaseResponse getSuccess(Object data) {
        BaseResponse r = new BaseResponse();
        r.code = SUCCESS_CODE;
        r.msg = "success";
        r.data = data;
        return r;
    }

    public static BaseResponse getError(String msg) {
        BaseResponse r = new BaseResponse();
        r.code = ERROR_CODE;
        r.msg = msg;
        return r;
    }

    public static BaseResponse buildSuccess(String msg) {
        BaseResponse r = new BaseResponse();
        r.code = SUCCESS_CODE;
        r.msg = msg;
        r.data = "";
        return r;
    }

}
