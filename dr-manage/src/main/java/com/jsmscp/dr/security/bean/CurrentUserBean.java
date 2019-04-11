package com.jsmscp.dr.security.bean;


import com.jsmscp.dr.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUserBean extends User {
    private Integer userId;

    private String nickName;

    private String userName;

//    private List<MenuDto> menus = new ArrayList<>();

    public CurrentUserBean(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        super(sysUser.getUserName(), sysUser.getPassword(), authorities);
        this.userId = sysUser.getId();
        this.nickName = sysUser.getNickName();
        this.userName = sysUser.getUserName();

    }

//    public void addMenu(MenuDto menu) {
//        this.menus.add(menu);
//    }



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

//    public List<MenuDto> getMenus() {
//        return menus;
//    }

//    public void setMenus(List<MenuDto> menus) {
//        this.menus = menus;
//    }
}
