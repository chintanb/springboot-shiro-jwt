package com.stormpath.shiro.samples.springboot;
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

import com.stormpath.shiro.samples.springboot.common.security.TokenUtil;
import io.jsonwebtoken.JwtException;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationTokenFilter extends PermissionsAuthorizationFilter {

    private static final int BEARER_TOKEN_START_INDEX = 7;

    private TokenUtil tokenUtil;

    private Logger log = LoggerFactory.getLogger(getClass());

    public AuthenticationTokenFilter() {
        this.tokenUtil = new TokenUtil();
    }

    public static final String TOKEN_HEADER = "Authorization";

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authorizationHeader = httpRequest.getHeader(TOKEN_HEADER);
        String authToken;
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
            authToken = authorizationHeader.substring(BEARER_TOKEN_START_INDEX);
            try {
                return tokenUtil.validateTokenAgainstSignature(authToken, "mySecret");
            } catch (JwtException ex) {
                log.info("JWT validation failed.", ex);
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
