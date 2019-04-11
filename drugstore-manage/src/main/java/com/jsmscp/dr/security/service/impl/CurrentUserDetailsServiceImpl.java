package com.jsmscp.dr.security.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.jsmscp.dr.entity.DrugStoreUser;
import com.jsmscp.dr.security.bean.CurrentUserBean;
import com.jsmscp.dr.service.DrugStoreUserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrentUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CurrentUserDetailsServiceImpl.class);

    @Autowired
    DrugStoreUserService drugStoreUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Authentication user with name = {}", username);
        DrugStoreUser user = drugStoreUserService.getNormalUserByName(username);

        if (null == user) {
            throw new UsernameNotFoundException("not find user");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        CurrentUserBean currentUser = new CurrentUserBean(user, authorities);
        return currentUser;
    }
}

