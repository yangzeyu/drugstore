package com.jsmscp.dr.interceptor;

import com.jsmscp.dr.service.PlatformOperateLogService;
import com.jsmscp.dr.util.HttpRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Autowired
    private PlatformOperateLogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 接收到请求，记录请求内容
        logger.info("接收请求");
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

        return true;
    }
}
