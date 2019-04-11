package com.jsmscp.dr.api.interceptor;

import com.jsmscp.dr.api.common.exception.BaseException;
import com.jsmscp.dr.api.service.DrugStoreService;
import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.util.StringUtils;
import com.jsmscp.dr.util.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    private DrugStoreService drugStoreService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String appCode = request.getParameter("appCode");
        if (StringUtils.isBlank(appCode)) {
            throw new BaseException("appCode不存在");
        }

        DrugStore store = drugStoreService.findByAppCode(appCode);

        if (store == null) {
            throw new BaseException("药店不存在");
        }

        String token = store.getAppToken();
        if (StringUtils.isBlank(token)) {
            throw new BaseException("药店token不存在");
        }
        String timestamp = request.getParameter("timestamp");
        if (StringUtils.isBlank(timestamp)) {
            throw new BaseException("timestamp不存在");
        }
        String signature = request.getParameter("signature");
        if (StringUtils.isBlank(signature)) {
            throw new BaseException("签名不存在");
        }
        String nonce = request.getParameter("nonce");
        if (nonce == null) {
            nonce = "";
        }
        String sign = VerifyUtils.getSignature(token, timestamp, nonce);
        if (!sign.equals(signature)) {
            throw new BaseException("签名验证未通过");
        }

        request.setAttribute("store", store);

        return true;
    }

    @Autowired
    public void setDrugStoreService(DrugStoreService drugStoreService) {
        this.drugStoreService = drugStoreService;
    }
}
