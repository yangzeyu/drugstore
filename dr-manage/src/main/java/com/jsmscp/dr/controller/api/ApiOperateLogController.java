package com.jsmscp.dr.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.service.ApiOperateLogService;
import com.jsmscp.dr.util.Response;

import java.util.HashMap;

@RestController
@RequestMapping("/api/apiOperateLog")
public class ApiOperateLogController {


    private ApiOperateLogService apiOperateLogService;

    @Autowired
    public ApiOperateLogController(ApiOperateLogService apiOperateLogService) {
        this.apiOperateLogService = apiOperateLogService;
    }

    /**
     * 查询接口连接操作日志
     *
     * @param operator
     * @param url
     * @param status
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list(@RequestParam(value = "operator", required = false) String operator,
                         @RequestParam(value = "url", required = false) String url,
                         @RequestParam(value = "status", required = false) String status,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endTime", required = false) String endTime,
                         @RequestParam(value = "pageNo") Integer pageNo) {
        HashMap<String, Object> map = apiOperateLogService.list(operator, url, status, startTime, endTime, pageNo);
        return Response.buildSuccess(map);
    }

}
