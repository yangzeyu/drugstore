package com.jsmscp.dr.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.jsmscp.dr.service.PlatformOperateLogService;
import com.jsmscp.dr.util.Response;

import java.util.HashMap;

@RestController
@RequestMapping("/api/operateLog")
public class PlatformOperateLogController {

    private PlatformOperateLogService platformOperateLogService;

    @Autowired
    public PlatformOperateLogController(PlatformOperateLogService platformOperateLogService) {
        this.platformOperateLogService = platformOperateLogService;
    }

    /**
     * 查询平台日志
     * @param operate  (操作,暂时未定不用显示)
     * @param operator (操作人)
     * @param startTime  (yyyy-MM-dd HHmmss)
     * @param endTime  (yyyy-MM-dd HHmmss)
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list(@RequestParam(value = "operate", required = false) String operate,
                         @RequestParam(value = "operator", required = false) String operator,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endTime", required = false) String endTime,
                         @RequestParam(value = "pageNo") Integer pageNo) {
        HashMap<String, Object> map = platformOperateLogService.list(operate, operator, startTime, endTime, pageNo);
        return Response.buildSuccess(map);
    }

}
