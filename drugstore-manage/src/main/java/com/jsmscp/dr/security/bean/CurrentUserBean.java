package com.jsmscp.dr.security.bean;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jsmscp.dr.dto.MenuDto;
import com.jsmscp.dr.entity.DrugStoreUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CurrentUserBean extends User {
    private Integer userId;

    private String nickName;

    private String userName;

    private Integer storeId;

    private List<MenuDto> menus = new ArrayList<>();

    public CurrentUserBean(DrugStoreUser user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserName(), user.getPassword(), authorities);
        this.userId = user.getId();
        this.nickName = user.getNickName();
        this.userName = user.getUserName();
        this.storeId = user.getStoreId();
    }

    public void addMenu(MenuDto menu) {
        this.menus.add(menu);
    }


    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<MenuDto> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuDto> menus) {
        this.menus = menus;
    }
}
