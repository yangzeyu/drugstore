package com.jsmscp.dr.controller.api;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice("com.jsmscp.dr.controller.api")
public class ApiAdvice {

    private Logger logger = LoggerFactory.getLogger(ApiAdvice.class);

    @ExceptionHandler(BusinessException.class)
    public Response baseExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(200);
        logger.error("", ex);
        return Response.fail( ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Response exceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(200);
        logger.error("系统异常", ex);
        return Response.fail("server inner error");
    }
}
