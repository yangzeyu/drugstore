package com.jsmscp.dr.service;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.entity.ApiUser;
import com.jsmscp.dr.entity.SysMenu;

import java.util.List;
import java.util.Map;

public interface ApiUserService {
    List<ApiUser> list(Integer pageNo, String keyword, Integer roleId, Integer partyId, Integer status);

    Map<String, Object> findCount(String keyword, Integer roleId, Integer partyId, Integer status);

    ApiUser getApiUserByName();

    ApiUser getNormalUserByName(String username);

    List<SysMenu> findMenuByUserId(Integer id);

    void editApiUser(Integer userId, String userName, String nickName, String oldPassword, String newPassword,
                     Byte status) throws BusinessException;

    ApiUser findOne(Integer userId);
}
