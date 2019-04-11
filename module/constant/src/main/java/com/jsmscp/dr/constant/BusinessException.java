package com.jsmscp.dr.constant;

public class BusinessException extends Exception {
    private String errorCode;

    public BusinessException() {

    }

    public BusinessException(String errorMessage) {
        super(errorMessage);
    }

    public BusinessException(String errorMessage, String errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getErrorMessage());
        this.errorCode = errorCodeEnum.getErrorCode();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
