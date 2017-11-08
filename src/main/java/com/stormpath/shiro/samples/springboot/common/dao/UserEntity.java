package com.stormpath.shiro.samples.springboot.common.dao;

import javax.persistence.*;

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

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class UserEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String id;

    private String username;

    //TODO: Test with different passwords' hash for the max length
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;


    public UserEntity() {

    }

    public UserEntity(String id, String username, String password, String firstName, String lastName, String email) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUserName() {
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
