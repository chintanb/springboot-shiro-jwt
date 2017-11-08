package com.stormpath.shiro.samples.springboot.common.configuration;
/****************************************************************************************
 * Copyright (C) 2016-2017 Solace Inc.
 *
 * This file is part of the MaaS
 *
 * MaaS source files can not be copied and/or distributed without the express
 * permission of Solace Inc.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 ****************************************************************************************/

import com.stormpath.shiro.samples.springboot.AuthenticationTokenFilter;
import com.stormpath.shiro.samples.springboot.common.dao.DefaultStormtrooperDao;
import com.stormpath.shiro.samples.springboot.common.dao.IdentityManagerRepository;
import com.stormpath.shiro.samples.springboot.common.dao.StormtrooperDao;
import com.stormpath.shiro.samples.springboot.common.dao.UserDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Default configuration for MaaS Gateway - IAM
 *
 * @author Tony Zoght
 * @author Chintan Barad
 * @author Kevin Lidstone
 */
@Configuration
@Order(101)
public class IamServiceConfiguration {

    private static final Logger log = LoggerFactory.getLogger(IamServiceConfiguration.class);

//    @Autowired
//    private IdentityManagerRepository identityManagerRepository;

    @Autowired
    private UserDAO userDAO;

    public IamServiceConfiguration() {

    }

    @Bean
    protected StormtrooperDao stormtrooperDao() {
        return new DefaultStormtrooperDao();
    }


    /**
     * FilterRegistrationBean
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        //filterRegistration.addUrlPatterns("/**");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
    }

    /**
     * @see org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        bean.setLoginUrl("/foo/login");
        bean.setUnauthorizedUrl("/unauthor");

        Map<String, Filter> filters = new HashMap<>();
        filters.put("perms", authenticationTokenFilter());
        filters.put("anon", new AnonymousFilter());
        bean.setFilters(filters);

        Map<String, String> chains = new HashMap<>();
        chains.put("/foo/login", "anon");
        chains.put("/unauthor", "anon");
        chains.put("/logout", "logout");
        chains.put("/base/**", "anon");
        chains.put("/css/**", "anon");
        chains.put("/layer/**", "anon");
        chains.put("/troopers/**", "perms");
        bean.setFilterChainDefinitionMap(chains);
        return bean;
    }

    /**
     * @see org.apache.shiro.mgt.SecurityManager
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(maaSSecurityRealm());
        return manager;
    }

    /**
     * @return
     */
    @Bean
    @DependsOn(value="lifecycleBeanPostProcessor")
    public MaaSSecurityRealm maaSSecurityRealm() {
        return new MaaSSecurityRealm(userDAO);
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilter() {
        return new AuthenticationTokenFilter();
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

//    @Bean
//    public TokenUtil getTokenUtil() {
//        return new TokenUtil();
//    }

}
