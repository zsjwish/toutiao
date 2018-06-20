package com.newcoder.toutiao.model;

/**
 * created by zsj in 17:49 2018/5/12
 * description:
 **/

public class User {

    /**
     * salt: 盐，用于密码加密使用MD5之前和密码拼接使用
     * headUrl:头像URL连接
     */
    private int id;

    private String name;

    private String password;

    private String salt;

    private String headUrl;

    public User() {

    }

    public User(String name) {
        this.name = name;
        this.password = "";
        this.salt = "";
        this.headUrl = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
