package com.jsmscp.dr.service.impl.exception;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 731644438878273584L;

    public ServiceException(String message) {
        super(message);
    }
}
