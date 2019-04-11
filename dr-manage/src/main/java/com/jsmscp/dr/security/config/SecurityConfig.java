package com.jsmscp.dr.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/bootstrap/**", "/css/**", "/js/**", "/plugins/**", "/**/*.png");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //过滤所有请求
         http
            .authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/login")
                .permitAll();
        //全部请求都不过滤
//          http
//                .authorizeRequests()
//                .anyRequest()
//                .permitAll();

//        http.anonymous().disable();
        http.csrf().disable();

    }

    @Bean
    @ConditionalOnMissingBean(ClassPathTldLoader.class)
    public ClassPathTldLoader classPathTldLoader() {
        return new ClassPathTldLoader();
    }
}
