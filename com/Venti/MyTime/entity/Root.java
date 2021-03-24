package com.Venti.MyTime.entity;

//管理员实体类
public class Root {

    private String rootName;
    private String rootAccountNumber;
    private String password;
    private String path;

    public Root(String rootName, String rootAccountNumber, String password, String path) {
        this.rootName = rootName;
        this.rootAccountNumber = rootAccountNumber;
        this.password = password;
        this.path = path;
    }

    public Root() {

    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getRootAccountNumber() {
        return rootAccountNumber;
    }

    public void setRootAccountNumber(String rootAccountNumber) {
        this.rootAccountNumber = rootAccountNumber;
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

    @Override
    public String toString() {
        return "Root{" +
                "rootName='" + rootName + '\'' +
                ", rootAccountNumber='" + rootAccountNumber + '\'' +
                ", password='" + password + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
