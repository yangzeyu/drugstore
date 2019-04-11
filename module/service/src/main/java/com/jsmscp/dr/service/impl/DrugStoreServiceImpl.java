package com.jsmscp.dr.service.impl;

import com.jsmscp.dr.dto.StoreOutInReportDto;
import com.jsmscp.dr.mapper.DrugStoreUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.constant.ErrorCodeEnum;
import com.jsmscp.dr.dto.DrugCatalogDto;
import com.jsmscp.dr.entity.DrugStore;
import com.jsmscp.dr.entity.DrugStoreUser;
import com.jsmscp.dr.mapper.DrugStoreMapper;
import com.jsmscp.dr.service.DrugStoreService;
import com.jsmscp.dr.service.SendEmailService;
import com.jsmscp.dr.util.GenUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class DrugStoreServiceImpl implements DrugStoreService {

    private static final Logger logger = LoggerFactory.getLogger(DrugStoreServiceImpl.class);

    private DrugStoreMapper drugStoreMapper;
    private SendEmailService sendEmailService;
    private DrugStoreUserMapper drugStoreUserMapper;

    @Autowired
    public DrugStoreServiceImpl(DrugStoreMapper drugStoreMapper, SendEmailService sendEmailService,
                                DrugStoreUserMapper drugStoreUserMapper) {
        this.drugStoreMapper = drugStoreMapper;
        this.sendEmailService = sendEmailService;
        this.drugStoreUserMapper = drugStoreUserMapper;
    }

    /**
     * 查询所有药店信息
     * @param keyword
     * @return
     */
    @Override
    public List<DrugStore> findAllStore(String keyword) {
        List<DrugStore> list = drugStoreMapper.findAllDrugStore(keyword, Constant.STATUS_SUCCESS);
        return list;
    }


    /**
     * 添加药店信息
     * @param storeName
     * @param code
     * @param address
     * @param status
     * @param contractPerson
     * @param contractPhone
     * @param contractEmail
     * @return
     * @throws BusinessException
     */
    @Override
    public Integer add(String storeName, String code, String address, Byte status, String contractPerson,
                       String contractPhone, String contractEmail)throws BusinessException {
        DrugStore isExistStore = drugStoreMapper.findByName(storeName, code);
        if (isExistStore == null) {
            String maxCode = drugStoreMapper.findMaxCode();
            Integer appCode;
            if (maxCode == null) {
                appCode =  10001;
            } else {
                appCode = Integer.valueOf(maxCode) + 1;
            }
            String appToken = GenUtil.genDrugStoreToken();

            DrugStore drugStore = new DrugStore();
            drugStore.setAddress(address);
            drugStore.setAppCode(appCode + "");
            drugStore.setAppToken(appToken);
            drugStore.setCode(code);
            drugStore.setContractPerson(contractPerson);
            drugStore.setContractPhone(contractPhone);
            drugStore.setContractEmail(contractEmail);
            drugStore.setName(storeName);
            drugStore.setStatus(status);
            drugStore.setCreateAt(new Date());
            drugStore.setUpdateAt(new Date());
            drugStoreMapper.insert(drugStore);
            sendEmailService.sendEmail(contractEmail, "您好！" + storeName +
                            "药店的appCode为：" + appCode +
                            "，appToken为：" + appToken +
                            "，药店登录地址为：" + Constant.LOGIN_URL +
                            "，药店接口路径为：" + Constant.API_URL
                    );
            logger.info("新增药店" + drugStore.getId() + appCode + appToken);
            return drugStore.getId();
        }
        throw new BusinessException(ErrorCodeEnum.DRUGSTORE_IS_EXIST.getErrorMessage());
    }

    /**
     * 修改药店信息
     * @param storeId
     * @param storeName
     * @param code
     * @param address
     * @param status
     * @param contractPerson
     * @param contractPhone
     * @param contractEmail
     * @throws BusinessException
     */
    @Override
    public void edit(Integer storeId, String storeName, String code, String address, Byte status, String contractPerson,
                     String contractPhone, String contractEmail) throws BusinessException {
        DrugStore isExistStore = drugStoreMapper.findByName(storeName, code);
        DrugStore existStore = drugStoreMapper.selectByPrimaryKey(storeId);
        if (isExistStore == null || isExistStore.getId().equals(storeId)) {
            DrugStore drugStore = new DrugStore();
            drugStore.setId(storeId);
            drugStore.setAddress(address);
            drugStore.setCode(code);
            drugStore.setContractPerson(contractPerson);
            drugStore.setContractPhone(contractPhone);
            drugStore.setContractEmail(contractEmail);
            drugStore.setName(storeName);
            drugStore.setStatus(status);
            drugStore.setUpdateAt(new Date());
            drugStoreMapper.updateByPrimaryKeySelective(drugStore);
            if (!isExistStore.getCode().equals(code) || (Objects.equals(status, Constant.STATUS_SUCCESS)
                    && Objects.equals(isExistStore.getStatus(), Constant.STATUS_FAIL))) {
            sendEmailService.sendEmail(contractEmail, "您好！" + storeName +
                            "药店的appCode为：" + drugStore.getAppCode() +
                            "，appToken为：" + drugStore.getAppToken() +
                            "，药店登录地址为：" + Constant.LOGIN_URL +
                            "，药店接口路径为：" + Constant.API_URL
                    );
                logger.info("修改药店" + isExistStore.getId() + isExistStore.getAppCode() + isExistStore.getAppToken());
            }
        } else {
            throw new BusinessException(ErrorCodeEnum.DRUGSTORE_IS_EXIST.getErrorMessage());
        }
    }


    /**
     * 查询药店信息列表
     * @param keyword
     * @param status
     * @param startTime
     * @param endTime
     * @param pageNo
     * @return
     */
    @Override
    public HashMap<String, Object> list(String keyword, Byte status, String startTime, String endTime,
                                        Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNo", pageNo);
        map.put("pageSize", Constant.DEFAULT_PAGE_SIZE);
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        List<DrugCatalogDto> list = drugStoreMapper.findDrugStore(keyword, status, startTime, endTime, pageNo,
                pageSize);
        int count = drugStoreMapper.findCount(keyword, status, startTime, endTime);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        map.put("list", list);
        return map;
    }


    /**
     * 发送信息给药店appCode和appToken
     * @param drugStoreId
     */
    @Override
    public void sendMessage(Integer drugStoreId) {
        DrugStore drugStore = drugStoreMapper.selectByPrimaryKey(drugStoreId);
        DrugStoreUser drugStoreUser = drugStoreUserMapper.findByStoreId(drugStore.getId());
        String context = "您好！" + drugStore.getName() +
                "药店的appCode为：" + drugStore.getAppCode() +
                "，appToken为：" + drugStore.getAppToken();
        if (drugStoreUser != null) {
            context += "，登陆账号为：" + drugStoreUser.getUserName() +
                    "，登陆初始密码为：" + Constant.INITIAL_PASSWORD;
        }
        context += "，药店登录地址为：" + Constant.LOGIN_URL +
                "，药店接口路径为：" + Constant.API_URL;
        sendEmailService.sendEmail(drugStore.getContractEmail(), context);
    }


    /**
     * 根据药店id查询药店
     * @param drugStoreId
     * @return
     */
    @Override
    public DrugStore findOne(String drugStoreId) {
        DrugStore drugStore = drugStoreMapper.findOne(drugStoreId);
        return drugStore;
    }

    @Override
    public List<DrugStoreUser> findStoreUser(Integer storeId) {
        List<DrugStoreUser> list = drugStoreUserMapper.findStoreUser(storeId);
        return list;
    }

    @Override
    public DrugStoreUser findUserById(Integer userId) {
        DrugStoreUser user = drugStoreUserMapper.selectByPrimaryKey(userId);
        return user;
    }

    @Override
    public HashMap<String, Object> findStoreOutIn(Integer storeId, String goodName, String startTime, String endTIme,
                                                  Integer pageNo) {
        HashMap<String, Object> map = new HashMap<>();
        Integer pageSize = Constant.DEFAULT_PAGE_SIZE;
        map.put("pageSize", pageSize);
        map.put("pageNo", pageNo);
        pageNo = (pageNo - 1) * pageSize;
        List<StoreOutInReportDto> list = drugStoreMapper.findStoreOutIn(storeId, goodName, startTime, endTIme, pageNo,
                pageSize);
        int count = drugStoreMapper.findStoreOutInCount(storeId, goodName, startTime, endTIme);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        map.put("list", list);
        return map;
    }


    /**
     * 药店进销存统计数据导出
     * @param storeId
     * @param goodName
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<StoreOutInReportDto> exportStore(Integer storeId, String goodName, String startTime, String endTime) {
       List<StoreOutInReportDto> list = drugStoreMapper.exportStore(storeId, goodName, startTime, endTime);
       return list;
    }

}
