package com.jsmscp.dr.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.entity.DrugStoreUser;
import com.jsmscp.dr.service.DrugStoreService;
import com.jsmscp.dr.service.DrugStoreUserService;
import com.jsmscp.dr.util.Response;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/drugStore")
public class DrugStoreController {

    private static final Logger LOG = LoggerFactory.getLogger(DrugStoreController.class);

    private DrugStoreService drugStoreService;

    private DrugStoreUserService drugStoreUserService;

    @Autowired
    public DrugStoreController(DrugStoreService drugStoreService, DrugStoreUserService drugStoreUserService) {
        this.drugStoreService = drugStoreService;
        this.drugStoreUserService = drugStoreUserService;
    }

    /**
     * 创建药店
     * @param storeName
     * @param code
     * @param address
     * @param status
     * @param contractPerson
     * @param contractPhone
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Response add (@RequestParam(value = "storeName") String storeName,
                          @RequestParam(value = "code") String code,
                          @RequestParam(value = "address", required = false) String address,
                          @RequestParam(value = "status") Byte status,
                          @RequestParam(value = "contractPerson", required = false) String contractPerson,
                          @RequestParam(value = "contractPhone", required = false) String contractPhone,
                          @RequestParam(value = "contractEmail") String contractEmail) {

        try {
            Integer id = drugStoreService.add(storeName, code, address, status, contractPerson, contractPhone,
                                                contractEmail);
            return Response.buildSuccess(id);
        } catch (BusinessException e) {
            LOG.error("", e);
            return Response.fail(e.getMessage());
        }
    }

    /**
     * 添加药店用户
     * @param userName
     * @param nickName
     * @param password
     * @param status
     * @return
     */
    @RequestMapping(value = "addStoreUser", method = RequestMethod.POST)
    public Response addStoreUser (@RequestParam(value = "userName") String userName,
                          @RequestParam(value = "nickName") String nickName,
                          @RequestParam(value = "storeId") Integer storeId,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "status") Byte status) {

        Integer id = null;
        try {
            id = drugStoreUserService.addStoreUser(userName, nickName, storeId, password, status);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return Response.buildSuccess(id);
    }



    /**
     * 修改药店用户
     * @param userId
     * @param userName
     * @param nickName
     * @param oldPassword
     * @param newPassword
     * @param status
     * @return
     */

    @RequestMapping(value = "editStoreUser", method = RequestMethod.POST)
    public Response addStoreUser (@RequestParam(value = "userName") String userName,
                          @RequestParam(value = "userId") Integer userId,
                          @RequestParam(value = "nickName") String nickName,
                          @RequestParam(value = "oldPassword") String oldPassword,
                          @RequestParam(value = "newPassword") String newPassword,
                          @RequestParam(value = "status") Byte status) {

        try {
            drugStoreUserService.editStoreUser(userId, userName, nickName, oldPassword, newPassword, status);
        } catch (BusinessException e) {
            return Response.fail(e.getMessage());
        }
        return Response.buildSuccess("");
    }


    /**
     * 修改药店信息
     * @param storeName
     * @param code
     * @param address
     * @param status
     * @param contractPerson
     * @param contractPhone
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public Response list (@RequestParam(value = "storeId") Integer storeId,
                          @RequestParam(value = "storeName") String storeName,
                          @RequestParam(value = "code") String code,
                          @RequestParam(value = "address", required = false) String address,
                          @RequestParam(value = "status") Byte status,
                          @RequestParam(value = "contractPerson", required = false) String contractPerson,
                          @RequestParam(value = "contractPhone", required = false) String contractPhone,
                          @RequestParam(value = "contractEmail") String contractEmail) {
        try {
            drugStoreService.edit(storeId, storeName, code, address, status, contractPerson, contractPhone,
                    contractEmail);
            return Response.success();
        } catch (BusinessException e) {
            LOG.error("", e);
            return Response.fail(e.getMessage());
        }
    }

    /**
     * 查询药店用户
     * @param storeId
     * @return
     */
    @RequestMapping(value = "findStoreUser", method = RequestMethod.GET)
    public Response findStoreUser (@RequestParam(value = "storeId") Integer storeId) {
            List<DrugStoreUser> list = drugStoreService.findStoreUser(storeId);
            return Response.buildSuccess(list);
    }

    /**
     * 查询药店用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "findUserById", method = RequestMethod.GET)
    public Response findUserById (@RequestParam(value = "userId") Integer userId) {
            DrugStoreUser user = drugStoreService.findUserById(userId);
            return Response.buildSuccess(user);
    }



    /**
     * 列表查询药店信息
     * @param keyword
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Response list (@RequestParam(value = "keyword", required = false) String keyword,
                          @RequestParam(value = "startTime", required = false) String startTime,
                          @RequestParam(value = "status", required = false) Byte status,
                          @RequestParam(value = "endTime", required = false) String endTime,
                          @RequestParam(value = "pageNo") Integer pageNo) {
        HashMap<String, Object> map = drugStoreService.list(keyword, status, startTime, endTime, pageNo);
        return Response.buildSuccess(map);
    }

    /**
     * 列表查询药店信息
     * @param drugStoreId
     * @return
     */
    @RequestMapping(value = "findOne", method = RequestMethod.GET)
    public Response list (@RequestParam(value = "drugStoreId") String drugStoreId) {
        DrugStore drugStore = drugStoreService.findOne(drugStoreId);
        return Response.buildSuccess(drugStore);
    }

    /**
     * 发送邮件
     * @param drugStoreId
     * @return
     */
    @RequestMapping(value = "sendMessage", method = RequestMethod.GET)
    public Response list (@RequestParam(value = "drugStoreId") Integer drugStoreId) {
        drugStoreService.sendMessage(drugStoreId);
        return Response.success();
    }



}
