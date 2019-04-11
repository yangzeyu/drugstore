package com.jsmscp.dr.service;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.entity.DrugStoreUser;

public interface DrugStoreUserService {
    DrugStoreUser getNormalUserByName(String userName);


    Integer addStoreUser(String userName, String nickName, Integer storeId, String password, Byte status)
            throws BusinessException;

    void editStoreUser(Integer userId, String userName, String nickName, String oldPassword, String newPassword,
                       Byte status) throws BusinessException;
}
