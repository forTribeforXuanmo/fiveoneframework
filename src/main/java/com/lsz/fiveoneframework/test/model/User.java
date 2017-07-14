package com.lsz.fiveoneframework.test.model;

import java.io.Serializable;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/10
 * time:17:42
 */
public class User implements Serializable {
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
