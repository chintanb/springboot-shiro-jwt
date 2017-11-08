
package com.stormpath.shiro.samples.springboot.common.model;

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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AuthenticationRequestDTO implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    @JsonProperty(required = true)
    private String username;

    @JsonProperty(required = true)
    private String password;

    public AuthenticationRequestDTO() {
    }

    public AuthenticationRequestDTO(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return null;
    }

}
