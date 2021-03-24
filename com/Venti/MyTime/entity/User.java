package com.Venti.MyTime.entity;

//用户实体类
public class User {

    private String username;
    private String userAccountNumber;
    private String password;
    private String path;
    private String createdTime;

    public User() {}

    public User(String username, String userAccountNumber, String password, String path,String createdTime) {
        this.username = username;
        this.userAccountNumber = userAccountNumber;
        this.password = password;
        this.path = path;
        this.createdTime = createdTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAccountNumber() {
        return userAccountNumber;
    }

    public void setUserAccountNumber(String userAccountNumber) {
        this.userAccountNumber = userAccountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userAccountNumber='" + userAccountNumber + '\'' +
                ", password='" + password + '\'' +
                ", path='" + path + '\'' +
                ",createdTime="+createdTime+'\''+
                '}';
    }
}
