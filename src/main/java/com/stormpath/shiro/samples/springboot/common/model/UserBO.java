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

public class UserBO {

    private String id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    public UserBO() {

    }

    public UserBO(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}