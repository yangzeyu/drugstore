package com.jsmscp.dr.api.handler;

import com.jsmscp.dr.api.common.BaseResponse;
import com.jsmscp.dr.api.common.exception.BaseException;
import com.jsmscp.dr.api.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice("com.jsmscp.dr")
@ResponseBody
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BaseException.class, ServiceException.class})
    public BaseResponse baseExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(200);
        return BaseResponse.getError(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse exceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(200);
        logger.error("系统异常", ex);
        return BaseResponse.getError("系统异常");
    }
}
