package com.jsmscp.dr.api.filter;

import com.alibaba.fastjson.JSON;
import com.jsmscp.dr.api.common.BaseResponse;
import com.jsmscp.dr.api.service.ApiLogService;
import com.jsmscp.dr.entity.ApiOperateLog;
import com.jsmscp.dr.util.HttpRequestUtils;
import com.jsmscp.dr.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebFilter(filterName = "accessLogFilter", urlPatterns = "/*")
public class AccessLogFilter implements Filter {

	private ApiLogService apiLogService;

	private static final Logger logger = LoggerFactory.getLogger(AccessLogFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);
		HttpServletRequest req = (HttpServletRequest) request;
		String appCode = request.getParameter("appCode");
		String url = req.getRequestURI();
		String ip = HttpRequestUtils.getIp(req);
		Date operateDate = new Date();
		Map<String, Object> params = new HashMap<>();
		Enumeration<?> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			params.put(paramName, request.getParameterValues(paramName));
		}

		chain.doFilter(request, wrapper);

		// 获取response返回的内容并重新写入response
		String result = wrapper.getResponseData(response.getCharacterEncoding());
		response.getOutputStream().write(result.getBytes());
		Integer status = ApiOperateLog.STATUS_ERROR;
		try {
			BaseResponse baseResponse = JSON.parseObject(result, BaseResponse.class);

			if (BaseResponse.SUCCESS_CODE == baseResponse.getCode()) {
				status = ApiOperateLog.STATUS_SUCCESS;
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		logger.info("appCode:{}, url:{}, ip : {}, req:{}, resp:{}", appCode, url, ip,
				JsonUtils.toJsonString(params), result);
		if (result.length() > 1000) {
			result = result.substring(0, 1000);
		}
		apiLogService.create(appCode, url, ip, params, status, result, operateDate);

	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
 
	}

	@Autowired
	public void setApiLogService(ApiLogService apiLogService) {
		this.apiLogService = apiLogService;
	}
}
