package com.jsmscp.dr.constant;

public enum ErrorCodeEnum {
    SYSTEM_ERROR("1001", "系统异常"),
    UPDATE_FAIL("1002", "更新失败"),
    SELECT_FAIL("1003", "查询失败"),
    DELETE_FAIL("1004", "删除失败"),
    MENU_CODE_EXIST("2001", "菜单名编码已存在"),
    MENU_NAME_EXIST("2002", "菜单名称已存在"),
    MENU_URL_EXIST("2003", "菜单url已存在"),
    CLASSIFY_NAME_EXIST("2004", "分类名称已存在"),
    GENERAL_NAME_EXIST("2005", "通用名已存在"),
    MANUFACTURE_NAME_EXIST("2006", "生产厂家名称已存在"),
    DRUG_NAME_EXIST("2007", "药品名称已存在"),
    DRUG_CODE_EXIST("2008", "药品编码已存在"),
    DRUG_EXIST("2009", "药品名称已存在"),
    ROLE_NAME_EXIST("2010", "角色名称存在"),
    ROLE_CODE_EXIST("2011", "角色编码存在"),
    USER_NAME_EXIST("2012", "用户名称已存在"),
    USER_PASSWORD_EXIST("2013", "密码验证失败"),
    USER_ADMIN_EXIST("2014", "该账户不允许删除"),
    WXGROUP_CODE_EXIST("3001", "组织编码已存在"),
    WXGROUP_NAME_EXIST("3002", "组织名称已存在"),
    APPCODE_IS_NULL("4001", "appCode为空"),
    NONCE_IS_NULL("4002", "随机数为空"),
    TIMESTAMP_IS_NULL("4003", "时间戳为空"),
    SIGNATURE_IS_NULL("4004", "签名为空"),
    PHARMACY_NOT_REGISTER("4005", "药店未在平台注册"),
    INSURANCECODE_OR_DRUGCODE_ISNULL("4006", "医保编码或药品编码为空"),
    DRUG_NOT_MATCH("4007", "药品未匹配成功"),
    DRUGCODE_IS_NULL("4008", "药品编码为空"),
    RECIPENO_IS_NULL("4009", "处方号不能为空"),
    RECIPENO_ISNOT_EXIST("4010", "处方号不存在"),
    SIGNATURE_ISNOT_EXIST("4011", "签名验证失败"),
    PARAMETER_ISNOT_EXIST("4012", "参数错误"),
    HOSPITAL_ISNOT_EXIST("4013", "医院code不存在"),
    RECIPE_ISNOT_NEW("4014", "处方已不是新开状态"),
    DRUGSTORE_IS_EXIST("4015", "药店已存在"),
    MEDICALINSURANCE_IS_USED("5001", "医保仍关联相关药品");




    private String errorCode;

    private String errorMessage;

    private ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private ErrorCodeEnum(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toString() {
        return "[" + this.errorCode + "]:" + this.errorMessage;
    }
}
