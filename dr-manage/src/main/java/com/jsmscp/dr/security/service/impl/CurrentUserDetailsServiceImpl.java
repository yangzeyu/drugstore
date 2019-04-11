package com.jsmscp.dr.security.service.impl;


import com.jsmscp.dr.entity.SysUser;
import com.jsmscp.dr.security.bean.CurrentUserBean;
import com.jsmscp.dr.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrentUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CurrentUserDetailsServiceImpl.class);

    @Autowired
    SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info("Authentication user with name = {}", username);
        SysUser user = sysUserService.getNormalUserByName(username);

        if (null == user) {
            throw new UsernameNotFoundException("not find user");
        }

        List<String> roles = sysUserService.findAllRoles(user.getId());
        List<GrantedAuthority> authorities = roles.stream().map(s -> new SimpleGrantedAuthority("ROLE_" + s))
                                                .collect(Collectors.toList());
//        List<SysMenu> menus = sysUserService.findMenuByUserId(user.getId());

        CurrentUserBean currentUser = new CurrentUserBean(user, authorities);
//        createMenu(currentUser, menus);
        return currentUser;
    }
//
//    private void createMenu(CurrentUserBean currentUser, List<SysMenu> menus) {
//        if (menus == null || menus.size() == 0) {
//            return;
//        }
//        for (SysMenu menu : menus) {
//            if (menu.getParentId().equals(Constant.SYS_MENU_TOP_PARENT_ID)) {
//                Integer topId = menu.getId();
//                MenuDto dto = new MenuDto();
//                convertMenu(dto, menu);
//                for (SysMenu sysMenu : menus) {
//                    if (topId.equals(sysMenu.getParentId())) {
//                        MenuDto subDto = new MenuDto();
//                        convertMenu(subDto, sysMenu);
//                        dto.addSubMenu(subDto);
//                    }
//                }
//                currentUser.addMenu(dto);
//            }
//        }
//    }
//
//    private void convertMenu(MenuDto dto, SysMenu menu) {
//        dto.setId(menu.getId());
//        dto.setName(menu.getName());
//        dto.setIcon(menu.getIcon());
//        dto.setParentId(menu.getParentId());
//        dto.setUrl(menu.getUrl());
//    }
}

