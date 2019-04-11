package com.jsmscp.dr.service.impl;

import com.jsmscp.dr.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.constant.ErrorCodeEnum;
import com.jsmscp.dr.entity.DrugStoreUser;
import com.jsmscp.dr.mapper.DrugStoreUserMapper;
import com.jsmscp.dr.service.DrugStoreUserService;

import java.util.Date;
import java.util.List;

@Service
public class DrugStoreUserServiceImpl implements DrugStoreUserService {

    private DrugStoreUserMapper drugStoreUserMapper;

    @Autowired
    public DrugStoreUserServiceImpl(DrugStoreUserMapper drugStoreUserMapper) {
        this.drugStoreUserMapper = drugStoreUserMapper;
    }

    /**
     * 根据用户名查询用户是否存在
     * @param userName
     * @return
     */
    @Override
    public DrugStoreUser getNormalUserByName(String userName) {
        List<DrugStoreUser> userList = drugStoreUserMapper.findUserWithoutDeleted(userName, Constant.STATUS_SUCCESS);
        return userList.isEmpty() ? null : userList.get(0);
    }


    /**
     * 添加药店用户信息
     * @param userName
     * @param nickName
     * @param password
     * @param status
     * @return
     */
    @Override
    public Integer addStoreUser(String userName, String nickName, Integer storeId, String password, Byte status)
            throws BusinessException {
        DrugStoreUser user = new DrugStoreUser();
        DrugStoreUser storeUser = drugStoreUserMapper.findByUserName(userName);
        if (storeUser != null) {
            throw new BusinessException(ErrorCodeEnum.USER_NAME_EXIST);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setUserName(userName);
        user.setNickName(nickName);
        user.setPassword(encoder.encode(password));
        user.setStoreId(storeId);
        user.setStatus(status);
        drugStoreUserMapper.insert(user);
        return user.getId();
    }

    /**
     * 修改药店用户信息
     * @param userId
     * @param userName
     * @param nickName
     * @param oldPassword
     * @param newPassword
     * @param status
     */
    @Override
    public void editStoreUser(Integer userId, String userName, String nickName, String oldPassword, String newPassword,
                              Byte status) throws BusinessException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        DrugStoreUser user = new DrugStoreUser();
        DrugStoreUser storeUser = drugStoreUserMapper.selectByPrimaryKey(userId);
        user.setUserName(userName);
        user.setNickName(nickName);
        user.setStatus(status);
        user.setId(userId);
        if (!StringUtils.isBlank(oldPassword)) {
            if (storeUser != null && encoder.matches(oldPassword, storeUser.getPassword())) {
                user.setPassword(encoder.encode(newPassword));
            } else {
                throw new BusinessException("密码验证失败，请重新输入！");
            }
        }
        drugStoreUserMapper.updateByPrimaryKeySelective(user);
    }
}
