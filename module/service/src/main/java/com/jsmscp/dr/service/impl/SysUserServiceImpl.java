package com.jsmscp.dr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsmscp.dr.constant.BusinessException;
import com.jsmscp.dr.constant.Constant;
import com.jsmscp.dr.constant.ErrorCodeEnum;
import com.jsmscp.dr.entity.SysMenu;
import com.jsmscp.dr.entity.SysRoleUserRelation;
import com.jsmscp.dr.entity.SysUser;
import com.jsmscp.dr.mapper.SysRoleUserRelationMapper;
import com.jsmscp.dr.mapper.SysUserMapper;
import com.jsmscp.dr.service.SysUserService;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    private SysUserMapper sysUserMapper;

    private SysRoleUserRelationMapper sysRoleUserRelationMapper;


    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper, SysRoleUserRelationMapper sysRoleUserRelationMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysRoleUserRelationMapper = sysRoleUserRelationMapper;
    }




    /**
     * 根据id查询单个用户
     * @param userId 用户id
     * @return
     */

    @Override
    public SysUser findOne(Integer userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    /**
     *新增用户信息
     * @return
     */

    @Override
    public Integer addUser(String nickName, String userName, String password, Integer roleId) throws BusinessException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        SysUser sysUser = sysUserMapper.findByUserName(userName);
        if (sysUser != null) {
            throw new BusinessException(ErrorCodeEnum.USER_NAME_EXIST);
        }
        SysUser user = new SysUser();
        user.setCreateAt(new Date());
        user.setIsDeleted(Constant.IS_DELETED_NO);
        user.setNickName(nickName);
        user.setPassword(encoder.encode(password));
        user.setRoleId(roleId);
        user.setStatus(Constant.STATUS_SUCCESS);
        user.setUserName(userName);
        sysUserMapper.insert(user);
        SysRoleUserRelation sysRoleUserRelation = new SysRoleUserRelation();
        sysRoleUserRelation.setRoleId(roleId);
        sysRoleUserRelation.setUserId(user.getId());
        sysRoleUserRelation.setCreateAt(new Date());
        sysRoleUserRelation.setUpdateAt(new Date());
        sysRoleUserRelationMapper.insert(sysRoleUserRelation);
        return user.getId();
    }

    /**
     * 更新用户
     * @param sysUser
     */
    public void updateUser(SysUser sysUser) {
        sysUserMapper.updateByPrimaryKeySelective(sysUser);

    }

    /**
     * 查看所有用户
     * @param pageNo
     * @return
     */
    @Override
    public List<SysUser> list(Integer pageNo, String key, Integer roleId, Integer status) {
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        pageNo = (pageNo - 1) * pageSize;
        return sysUserMapper.list(pageSize, pageNo, key, roleId, status, Constant.IS_DELETED_NO);
    }

    /**
     * 删除用户
     * @param id
     * @throws Exception
     */
    @Override
    public void updateIsDeleted(Integer id) {
        SysUser user = new SysUser();
        user.setIsDeleted(Constant.IS_DELETED_YES);
        user.setId(id);
        user.setUpdateAt(new Date());
        sysUserMapper.updateByPrimaryKeySelective(user);
    }


    /**
     * 查看用户总数
     * @return
     */
    @Override
    public HashMap<String, Object> findTotal(String key, Integer roleId, Integer status) {
        Byte pageSize = Constant.DEFAULT_PAGE_SIZE;
        HashMap<String, Object> map = new HashMap<>();
        int count = sysUserMapper.findCount(key, roleId, status, Constant.IS_DELETED_NO);
        int total = count / pageSize;
        if (count % pageSize > 0) {
            total += 1;
        }
        map.put("count", count);
        map.put("total", total);
        return map;
    }

    /**
     * 更新用户密码
     * @param userId
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    @Override
    public int updatePassword(Integer userId, String oldPassword, String newPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        SysUser user = sysUserMapper.selectByPrimaryKey(userId);
        if (user != null && encoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(encoder.encode(newPassword));
            user.setUpdateAt(new Date());
            return sysUserMapper.updateByPrimaryKeySelective(user);
        } else {
            return 0;
        }
    }

    /**
     * 修改用户信息
     * @param userId
     * @param nickName
     * @param userName
     * @param roleId
     */
    @Override
    public void update(Integer userId, String nickName, String userName, Integer roleId)
            throws BusinessException {
        SysUser sysUserById = sysUserMapper.selectByPrimaryKey(userId);
        SysUser sysUser = sysUserMapper.findByUserName(userName);
        if (sysUser != null && !sysUser.getUserName().equals(sysUserById.getUserName())) {
            throw new BusinessException(ErrorCodeEnum.USER_NAME_EXIST);
        }
        SysUser user = new SysUser();
        user.setUpdateAt(new Date());
        user.setUserName(userName);
        user.setRoleId(roleId);
        user.setNickName(nickName);
        user.setId(userId);
        sysUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public SysUser getNormalUserByName(String username) {
        List<SysUser> userList = sysUserMapper.findUserWithoutDeleted(username, Constant.IS_DELETED_NO);
        return userList.isEmpty() ? null : userList.get(0);
    }

    @Override
    public List<SysMenu> findMenuByUserId(Integer id) {
        return sysUserMapper.findMenuByUserId(id, Constant.IS_DELETED_NO);
    }

    @Override
    public List<String> findAllRoles(Integer userId) {
        return sysUserMapper.findAllRoles(userId);
    }


}
