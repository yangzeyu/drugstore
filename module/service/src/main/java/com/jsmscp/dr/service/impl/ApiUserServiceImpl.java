package com.jsmscp.dr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.constant.ErrorCodeEnum;
import com.jsmscp.dr.entity.ApiUser;
import com.jsmscp.dr.entity.SysMenu;
import com.jsmscp.dr.mapper.ApiUserMapper;
import com.jsmscp.dr.service.ApiUserService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiUserServiceImpl implements ApiUserService {

    private ApiUserMapper apiUserMapper;

    @Autowired
    public ApiUserServiceImpl(ApiUserMapper apiUserMapper) {
        this.apiUserMapper = apiUserMapper;
    }


    /**
     * 查询用户列表
     * @param pageNo
     * @param keyword
     * @param roleId
     * @param partyId
     * @param status
     * @return
     */
    @Override
    public List<ApiUser> list(Integer pageNo, String keyword, Integer roleId, Integer partyId, Integer status) {
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        List<ApiUser> list = apiUserMapper.list(pageNo, keyword, roleId, partyId, status, pageSize);
        return list;
    }

    /**
     *
     * 查询用户列表数量
     * @param keyword
     * @param roleId
     * @param partyId
     * @param status
     * @return
     */
    @Override
    public Map<String, Object> findCount(String keyword, Integer roleId, Integer partyId, Integer status) {
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        HashMap<String, Object> map = new HashMap<>();
        int count = apiUserMapper.findCount(keyword, roleId, partyId, status);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        return map;
    }

    /**
     * 根据用户名查询用户
     * @return
     */
    @Override
    public ApiUser getApiUserByName() {
        ApiUser apiUser = apiUserMapper.getApiUserByName();
        return apiUser;
    }

    /**
     * 根据用户名称查询是否存在用户
     * @param username
     * @return
     */
    @Override
    public ApiUser getNormalUserByName(String username) {
        List<ApiUser> userList = apiUserMapper.findUserWithoutDeleted(username, Constant.STATUS_SUCCESS);
        return userList.isEmpty() ? null : userList.get(0);
    }

    /**
     * 根据用户查询用户菜单
     * @param id
     * @return
     */
    @Override
    public List<SysMenu> findMenuByUserId(Integer id) {
        return apiUserMapper.findMenuByUserId(id, Constant.IS_DELETED_NO);
    }


    /**
     * 修改系统用户信息
     * @param userId
     * @param userName
     * @param nickName
     * @param oldPassword
     * @param newPassword
     * @param status
     * @throws BusinessException
     */
    @Override
    public void editApiUser(Integer userId, String userName, String nickName, String oldPassword, String newPassword,
                            Byte status) throws BusinessException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        ApiUser user = apiUserMapper.selectByName(userName);
        if (user != null) {
            user.setId(userId);
            user.setUserName(userName);
            user.setNickName(nickName);
            user.setStatus(status);
            user.setUpdateAt(new Date());
            if (oldPassword != null && encoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(encoder.encode(newPassword));
                apiUserMapper.updateByPrimaryKeySelective(user);
            } else {
                throw new BusinessException(ErrorCodeEnum.USER_PASSWORD_EXIST);
            }
        }
    }

    /**
     * 根据用户id查询用户
     * @param userId
     * @return
     */
    @Override
    public ApiUser findOne(Integer userId) {
        ApiUser user = apiUserMapper.selectByPrimaryKey(userId);
        return user;
    }

}
