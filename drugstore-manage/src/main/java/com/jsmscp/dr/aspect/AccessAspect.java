package com.jsmscp.dr.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jsmscp.dr.service.PlatformOperateLogService;
import com.jsmscp.dr.util.HttpRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AccessAspect {
    private static final Logger logger = LoggerFactory.getLogger(AccessAspect.class);

    @Autowired
    private PlatformOperateLogService logService;

    @Pointcut("execution(* com.jsmscp.dr.controller.*.*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")

    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        logger.info("接收请求");

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容

        logger.info("URL : {}, time: {}, IP : {}", request.getRequestURL().toString(), LocalDateTime.now(),
                HttpRequestUtils.getIp(request));
        logger.info("用户 : " + request.getRemoteUser());
        //获取所有参数方法一：

        Enumeration<String> enu = request.getParameterNames();
        Map<String, Object> map = new HashMap<>();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            map.put(paraName, request.getParameter(paraName));
            logger.info(paraName + ": " + request.getParameter(paraName));
        }

        logService.insertLogs(request.getRequestURL(), request.getRemoteUser(), HttpRequestUtils.getIp(request), map);
    }

    @AfterReturning("webLog()")
    public void doAfterReturning(JoinPoint joinPoint) {
        // 处理完请求，返回内容
        logger.info(new Date().toString());
        logger.info("请求结束");
    }


}
