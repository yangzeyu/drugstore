package com.jsmscp.dr.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.util.Response;

@Aspect
@Component
public class ApiAspect {
    private static final Logger logger = LoggerFactory.getLogger(ApiAspect.class);


    @Pointcut("execution(* com.jsmscp.dr.controller.api.*.*(..))")
    public void apiPayReceive() {

    }

    @Around("apiPayReceive()")
    public Object aroundApi(ProceedingJoinPoint joinPoint) {
        Object result = null;
        Response resp = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            if (throwable instanceof BusinessException) {
                resp = Response.fail( throwable.getMessage());
            } else {
                resp = Response.fail("server inner error");
            }
            logger.error("{}", throwable);
        }

        return null == resp ? result : resp;
    }
}
